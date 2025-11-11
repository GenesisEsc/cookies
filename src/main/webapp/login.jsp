<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Iniciar sesión</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/styles.css">
</head>
<body>

<div class="login-container">
    <h1>Iniciar sesión</h1>

    <form action="<%= request.getContextPath() %>/login" method="post">
        <div>
            <label for="user">Usuario</label><br>
            <input type="text" id="user" name="user" placeholder="Ingrese su usuario" required>
        </div>

        <div>
            <label for="password">Contraseña</label><br>
            <input type="password" id="password" name="password" placeholder="Ingrese su contraseña" required>
        </div>

        <div>
            <input type="submit" value="Entrar">
        </div>
    </form>
</div>

</body>
</html>

