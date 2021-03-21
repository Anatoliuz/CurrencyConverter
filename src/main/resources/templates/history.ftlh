<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Конвертер</title>
    <link rel="stylesheet" type="text/css" href="static/css/style.css"/>
</head>


<body>
<div class="topnav">
    <a href=/converter>Конвертер</a>
    <a class="active" href=/history>История</a>
    <a class="right" href=/logout>Выйти</a>
</div>
<div class="history-page">
    <div class="form">
        <form action="/history" method="POST">
            <h2>История</h2>

            <div id="grid-three">

                <div>
                    <input
                            <#if date??>
                                value="${date}"
                            </#if>
                            type="date" name="date">
                </div>
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
                                    <#if inputCurrencyId?? && cur.id == outputCurrencyId>
                                        selected
                                    </#if>
                                    label="${cur.name}">${cur.id}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div>
                <table>
                    <tr>
                        <th>Исходная валюта</th>
                        <th>Целевая валюта</th>
                        <th>Исходная сумма</th>
                        <th>Получаемая сумма</th>
                        <th>Дата</th>
                    </tr>
                    <#list historyList as history>
                        <tr>
                            <td>${history.inCur}</td>
                            <td>${history.outCur}</td>
                            <td>${history.inSum}</td>
                            <td>${history.outSum}</td>
                            <td>${history.date}</td>
                        </tr>
                    </#list>
                </table>
            </div>
            <button id="btn"> Поиск</button>

        </form>
    </div>
</div>

</body>
</html>