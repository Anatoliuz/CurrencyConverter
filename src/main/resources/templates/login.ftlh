<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Конвертер</title>
    <link rel="stylesheet" type="text/css" href="static/css/style.css"/>
</head>
<body>

<div class="login-page">
    <div class="form">
        <h2>Конвертер валют</h2>
        <form action="/login" method="POST" class="login-form">
            <input name="username" type="text" placeholder="Логин"/>
            <input name="password" type="password" placeholder="Пароль"/>
            <#if error??>
                <a class="error-text">Неверный логин или пароль</a>
            </#if>
            <button>Войти</button>
        </form>
    </div>
</div>

</body>
</html>