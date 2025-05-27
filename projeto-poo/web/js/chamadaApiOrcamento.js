document.getElementById("formProduto").addEventListener("submit", function(e) {
    e.preventDefault();
    gerarOrcamento('produto');
}); 

document.getElementById("formServico").addEventListener("submit", function(e) {
    e.preventDefault(); //Impede que a página recarregue
    gerarOrcamento('servico');//Chama a função especificando o tipo
});

function gerarOrcamento(tipo) {
    //Cria um objeto vazio para armazenar dados
    const dadosForm = {};
    
    //Pega os dados do formulário
    if (tipo === 'produto') {
        dadosForm.tipo = "produto";
        dadosForm.nome = document.getElementById('produto_nome').value;
        dadosForm.materiais = document.getElementById('produto_materiais').value;
        dadosForm.custo = document.getElementById('produto_custo').value;
        dadosForm.lucro = document.getElementById('produto_lucro').value;
    } else {
        dadosForm.tipo = "servico";
        dadosForm.descricao = document.getElementById('servico_descricao').value;
        dadosForm.horas = document.getElementById('servico_horas').value;
        dadosForm.valor_hora = document.getElementById('servico_valor_hora').value;
        dadosForm.custos_extras = document.getElementById('servico_custos_extras').value || 0;
    }
    
    // Mostra a mensagem de "carregando..." enquanto aguarda a resposta da API
    document.getElementById('resultado').innerHTML = `
        <p class="text-warning">⏳ Gerando orçamento, por favor aguarde...</p>
    `;
    
    //Envia os dados para a API
    fetch('http://localhost:8080/projeto-poo/OrcamentoApi', {
        method: 'POST',
        
        //Tipo de conteudo que a API espera (mantido como x-www-form-urlencoded)
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        
        //Converte os dados para o formato da API
        body: new URLSearchParams(dadosForm).toString()
        
    })
    .then(async resposta => {
        // Primeiro tentamos ler como texto para debug
        const textoResposta = await resposta.text();
        
        try {
            // Depois tentamos parsear como JSON
            const dados = JSON.parse(textoResposta);
            
            // Se tiver erro no JSON
            if (dados.erro) {
                throw new Error(dados.erro);
            }
            
            // Se chegou aqui, mostra o resultado
            document.getElementById('resultado').innerHTML = `
                <h3 class="mb-3">Orçamento Gerado:</h3>
                <div class="bg-dark p-3 rounded border border-light">
                    ${dados.resposta.replace(/\n/g, '<br>')}
                </div>
            `;
            
        } catch (e) {
            // Se falhar ao parsear JSON, mostra o erro original
            throw new Error(`Resposta inválida da API: ${textoResposta.substring(0, 100)}...`);
        }
    })
    .catch(erro => {
        // Mostra erro formatado
        document.getElementById('resultado').innerHTML = `
            <div class="alert alert-danger">
                Erro ao processar orçamento: ${erro.message}
            </div>
        `;
        console.error("Erro detalhado:", erro);
    });
};