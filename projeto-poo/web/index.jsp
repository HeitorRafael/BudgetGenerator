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
    
    <link rel="stylesheet" href="style/style.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    
    <style>
        body {
            background-color: #363636;
        }

        /* Fundo do formulário */
        .bg-form {
            background-color: #2d2d2d;
            color: white;
            border-radius: 12px;
        }

        /* Estilo dos inputs */
        .form-control {
            background-color: #444;
            border: none;
            color: white;
            margin-bottom: 10px;
        }

        .form-control:focus {
            background-color: #555;
            color: white;
            border: 1px solid #FF894C;
            box-shadow: none;
        }

        /* Botão customizado */
        .btn-custom {
            background: linear-gradient(45deg, #DC2F2F, #FF894C, #F7374F);
            border: none;
            color: white;
            padding: 10px 20px;
            font-size: 16px;
            font-weight: bold;
            border-radius: 8px;
            transition: opacity 0.3s ease-in-out;
        }

        .btn-custom:hover {
             opacity: 0.8; 
        }   
    </style>
    
</head>
<body>
    <div class="container vh-100 d-flex justify-content-center align-items-center">
        <div class="row w-50 bg-form p-4 shadow rounded">
            <form action="action">
                <div class="row">
                    
                    <div class="col-md-6">
                        <label class="form-label">Produto/Serviço</label>
                        <input type="text" id="produto" name="produto" class="form-control mt-2" placeholder="">

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
