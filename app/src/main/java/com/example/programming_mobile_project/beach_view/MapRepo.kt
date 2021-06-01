package com.example.programming_mobile_project.beach_view

// Contiene i metodi per implementare la mappa svg nella webView
class MapRepo {
    fun getHTMLBody(svgString: String, stringJS: String = getJS(), stringCSS: String = getCSS()) =
        "<!DOCTYPE HTML>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=10\">\n" +
                "<style>" + stringCSS + " </style>" +
                "    <script type='text/javascript'>" + stringJS + "</script>\n" +
                "</head>\n" +
                "\n" +
                "<body onload='initListener()'>\n" +
                "    <div id=\"div\">\n" +
                "\t\n" +
                svgString +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>"


    private fun getCSS(): String = "" +
            "    .selected {\n" +
            "      fill: lightgreen;\n" +
            "      fill-opacity: 0.7;\n" +
            "    }\n" +
            "\n" +
            "    .occupato {\n" +
            "      fill: #c05454;\n" +
            "      fill-opacity: 0.8;\n" +
            "    }\n" +
            "\n" +
            "    .ombrello {\n" +
            "      display: flex;\n" +
            "      flex-direction: column;\n" +
            "      align-items: center;\n" +
            "      float: left;\n" +
            "      margin: 1em;\n" +
            "    }\n" +
            "\n" +
            "    .row::after {\n" +
            "      content: \"\";\n" +
            "      display: table;\n" +
            "      clear: both;\n" +
            "    }\n" +
            "\n" +
            "    .row {\n" +
            "      display: flex\n" +
            "    }"

    private fun getJS(): String {
        return "" +
                "var ombrello = '';\n" +
                "    // Funzione invocata al caricamento della pagina\n" +
                "    function initListener() {\n" +
                "      const occupati = [1, 4, 6];\n" +
                "      \n" +
                "      query = '*[id^=ombr_]';\n" +
                "      var ombrelli = document.querySelectorAll(query);\n" +
                "      i = 0;\n" +
                "\n" +
                "      // Agli ombrelloni liberi aggiungo il click listener. \n" +
                "      // A quelli occupati applico lo stile 'occupato' e non aggiungo il listener\n" +
                "      for (i = 0; i < ombrelli.length; i++) {\n" +
                "        //se in occupati c'è l'i-esimo ombrello allora questo è occupato\n" +
                "        if (occupati.includes(i + 1)) {\n" +
                "          ombrelli[i].classList.add('occupato');\n" +
                "        }\n" +
                "        else {\n" +
                "          ombrelli[i].addEventListener('click', onClickOmbrello);\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "\n" +
                "    // Applica all'elemento cliccato lo stile 'selected'\n" +
                "    function onClickOmbrello(e) {\n" +
                "      if (ombrello !== '') {\n" +
                "        document.getElementById('ombr_' + ombrello).classList.toggle('selected');\n" +
                "      }\n" +
                "      ombrello = e.target.id.split('_')[1];\n" +
                "      document.getElementById(e.target.id).classList.toggle('selected');\n" +
                "      Android.clickedOmbrello(e.target.id);\n" +
                "    }\n"
    }
}