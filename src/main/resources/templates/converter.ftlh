<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Конвертер</title>
    <link rel="stylesheet" type="text/css" href="static/css/style.css"/>
</head>


<body>
<div class="topnav">
    <a class="active" href=/converter>Конвертер</a>
    <a href=/history>История</a>
    <a class="right" href=/logout>Выйти</a>
</div>
<div class="conv-page">

    <div class="form">
        <form action="/converter" method="POST">

            <h2>Конвертер</h2>

            <div id="grid">

                <div>
                    <select name="inputCurrencyId">
                        <#list currencies as cur>
                            <option
                                    <#if inputCurrencyId?? && cur.id == inputCurrencyId>
                                        selected
                                    </#if>
                                    label="${cur.name}">${cur.id}</option>
                        </#list>
                    </select>
                </div>
                <div>
                    <select name="outputCurrencyId">
                        <#list currencies as cur>
                            <option
                                    <#if outputCurrencyId?? && cur.id == outputCurrencyId>
                                        selected
                                    </#if>
                                    label="${cur.name}">${cur.id}</option>
                        </#list>
                    </select>
                </div>
                <div>

                    <input
                            <#if amount??>
                                value="${amount}"
                            </#if>
                            name="amount" type="number" placeholder="Количество">

                </div>
                <div>
                    <input
                            <#if result??>
                                value="${result}"
                            </#if>
                            readonly disabled placeholder="Количество">
                </div>
            </div>
            <button>Конвертировать</button>

        </form>
    </div>
</div>

</body>
</html>