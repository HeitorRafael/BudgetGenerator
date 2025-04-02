<%-- 
    Document   : index
    Created on : 28 de mar. de 2025, 20:10:44
    Author     : alexo
--%>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Formulário de Cadastro</title>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link href="style/style.css" rel="stylesheet" type="text/css"/>

    
    
    
</head>
<body>
    <div class="container vh-100 d-flex justify-content-center align-items-center">
        <div class="row w-50 bg-form p-4 shadow rounded">
            <form action="action" method="get">
                <div class="row">
                    
                    <div class="col-md-6">
                        <label class="form-label">Produto/Serviço</label>
                        <input type="text" id="produto_servico" name="produto_servico" class="form-control mt-2" placeholder="">

                        <label class="form-label">Materiais utilizados</label>
                        <input type="text" id="materiais" name="materiais" class="form-control mt-2" >

                        <label class="form-label">Tempo estimado de trabalho</label>
                        <input type="text" id="tempo" name="tempo" class="form-control mt-2">
                    </div>

                    
                    <div class="col-md-6">
                        <label class="form-label">Mão de obra</label>
                        <input type="text" id="mao_de_obra" name="mao_de_obra" class="form-control mt-2">

                        <label class="form-label">Margem de lucro (%)</label>
                        <input type="text" id="lucro" name="lucro" class="form-control mt-2">

                        <label class="form-label">Custos fixos</label>
                        <input type="text" id="custos" name="custos" class="form-control mt-2">
                    </div>
                </div>

                
                <div class="text-center mt-4">
                    <button type="submit" class="btn btn-custom w-100">Enviar</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>

</html>
