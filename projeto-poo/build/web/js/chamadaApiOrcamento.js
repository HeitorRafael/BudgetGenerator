  
  
  document.getElementById("orcamentoForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    //Guarda os inputs fornecidos pelo usuario, e guarda em um objeto
    const formData = {
      produto_servico: document.getElementById("produto_servico").value,
      materiais: document.getElementById("materiais").value,
      tempo: document.getElementById("tempo").value,
      mao_de_obra: document.getElementById("mao_de_obra").value,
      lucro: document.getElementById("lucro").value,
      custos: document.getElementById("custos").value
    };

    //Prompt com os dados do formulario para a IA
    const prompt = `Gere um orçamento para o serviço "${formData.produto_servico}",
     utilizando os materiais: ${formData.materiais}. O tempo estimado é ${formData.tempo},
     mão de obra custa ${formData.mao_de_obra}, margem de lucro de ${formData.lucro}%,
     e custos fixos de ${formData.custos}.`;

    try {
        //Requisição post para a API, e envia o prompt JSON
      const response = await fetch('http://localhost:8080/OrcamentoApi', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ prompt }) // Envia só o prompt para a IA
      });

      alert(result.resposta);
    } catch (err) {
      console.error("Erro ao enviar para API:", err);
    }
  });

