document.getElementById("formOrcamento").addEventListener("submit", async function (e) {
    e.preventDefault();

    //Pega os dados do formulário
    const dadosForm = {
        produto_servico: document.getElementById('produto_servico').value,
        materiais: document.getElementById('materiais').value,
        tempo: document.getElementById('tempo').value,
        mao_de_obra: document.getElementById('mao_de_obra').value,
        lucro: document.getElementById('lucro').value,
        custos: document.getElementById('custos').value
    };
    
    //Envia os dados para a API
    fetch('http://localhost:8080/projeto-poo/OrcamentoApi', {
        method: 'POST',
        
        //Tipo de conteudo que a API espera
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        
        //Converte os dados para o formato da API
        body: new URLSearchParams(dadosForm).toString()
        
    })//Pega a resposta da API e converte para JSON
    .then(resposta => resposta.json())
    
    .then(dados => {
        //Reposta da API
        document.getElementById('resultado').innerHTML = `
            <h3>Prompt gerado:</h3>
            <p>${dados.prompt.replace(/\n/g, '<br>')}</p>
        `;
        
    })
    .catch(erro => {
        document.getElementById('resultado').innerHTML = 'Erro ao chamar a API' + erro.message
    });
    
});