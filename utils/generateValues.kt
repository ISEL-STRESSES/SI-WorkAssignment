package utils

import java.io.PrintWriter
import java.time.LocalDate
import java.util.*

//package scratch
//
//import java.io.BufferedReader
//import java.io.FileReader
//
//fun createReader(fileName: String): BufferedReader {
//    return BufferedReader(FileReader(fileName))
//}
//
//
//fun main() {
//    println("File name: ")
//    val fileName = "src/main/kotlin/scratch/createTable.sql" // readln()
//    val tables = readTables(fileName)
//    readTableParameters(tables, true)
//}
//
///**
// * Creates a list of tables and for each table a list of parameters with the name of the type of the parameter
// * @param tables list of tables
// * @return list of tables and for each table a list of parameters with the name of the type of the parameter
// */
//fun readTableParameters(tables : MutableList<List<String>>, debug: Boolean = false): MutableList<List<Pair<String, String>>> {
//    val tableParameters = mutableListOf<List<Pair<String, String>>>()
//    for (table in tables) {
//        val parameters = table.take(1).takeLast(1).map { it.trim().split(" ") }.map { Pair(it.first(), it.take(1).joinToString(" ")) }
//        tableParameters.add(parameters)
//    }
//    if(debug) {
//        tableParameters.forEachIndexed { idx, elem ->
//            val tableName = tables[idx].first().split(" ").first{ it.contains("(")}
//            println("Table: $tableName")
//            println(elem.joinToString("\n"))
//        }
//    }
//    return tableParameters
//}
//
//fun readTables(fileName: String, debug: Boolean = false): MutableList<List<String>> {
//    val reader = createReader(fileName)
//    var line: String?
//    val tables = mutableListOf<List<String>>()
//    while (reader.readLine().also { line = it } != null) {
//        if(line!!.contains("CREATE TABLE")) {
//            val table = mutableListOf<String>()
//            table.add(line!!)
//            while (reader.readLine().also { line = it } != null) {
//                table.add(line!!)
//                if(line!!.contains(");")) {
//                    break
//                }
//            }
//            tables.add(table)
//        }
//    }
//    if(debug) {
//        tables.forEach {
//            println(it.joinToString("\n"))
//        }
//    }
//    return tables
//}

//fun main() {
//
//    generateInserts(
//        tableName = "user",
//        columnsName = listOf("name", "email"),
//        columnsType = listOf("Maria", ""),
//        size = 100
//    )
//}
val countries = listOf(
    "Afghanistan",
    "Albania",
    "Algeria",
    "Andorra",
    "Angola",
    "Antigua & Deps",
    "Argentina",
    "Armenia",
    "Australia",
    "Austria",
    "Azerbaijan",
    "Bahamas",
    "Bahrain",
    "Bangladesh",
    "Barbados",
    "Belarus",
    "Belgium",
    "Belize",
    "Benin",
    "Bhutan",
    "Bolivia",
    "Bosnia Herzegovina",
    "Botswana",
    "Brazil",
    "Brunei",
    "Bulgaria",
    "Burkina",
    "Burundi",
    "Cambodia",
    "Cameroon",
    "Canada",
    "Cape Verde",
    "Central African Rep",
    "Chad",
    "Chile",
    "China",
    "Colombia",
    "Comoros",
    "Congo",
    "Congo {Democratic Rep}",
    "Costa Rica",
    "Croatia",
    "Cuba",
    "Cyprus",
    "Czech Republic",
    "Denmark",
    "Djibouti",
    "Dominica",
    "Dominican Republic",
    "East Timor",
    "Ecuador",
    "Egypt",
    "El Salvador",
    "Equatorial Guinea",
    "Eritrea",
    "Estonia",
    "Ethiopia",
    "Fiji",
    "Finland",
    "France",
    "Gabon",
    "Gambia",
    "Georgia",
    "Germany",
    "Ghana",
    "Greece",
    "Grenada",
    "Guatemala",
    "Guinea",
    "Guinea-Bissau",
    "Guyana",
    "Haiti",
    "Honduras",
    "Hungary",
    "Iceland",
    "India",
    "Indonesia",
    "Iran",
    "Iraq",
    "Ireland {Republic}",
    "Israel",
    "Italy",
    "Ivory Coast",
    "Jamaica",
    "Japan",
    "Jordan",
    "Kazakhstan",
    "Kenya",
    "Kiribati",
    "Korea North",
    "Korea South",
    "Kosovo",
    "Kuwait",
    "Kyrgyzstan",
    "Laos",
    "Latvia",
    "Lebanon",
    "Lesotho",
    "Liberia",
    "Libya",
    "Liechtenstein",
    "Lithuania",
    "Luxembourg",
    "Macedonia",
    "Madagascar",
    "Malawi",
    "Malaysia",
    "Maldives",
    "Mali",
    "Malta",
    "Marshall Islands",
    "Mauritania",
    "Mauritius",
    "Mexico",
    "Micronesia",
    "Moldova",
    "Monaco",
    "Mongolia",
    "Montenegro",
    "Morocco",
    "Mozambique",
    "Myanmar, {Burma}",
    "Namibia",
    "Nauru",
    "Nepal",
    "Netherlands",
    "New Zealand",
    "Nicaragua",
    "Niger",
    "Nigeria",
    "Norway",
    "Oman",
    "Pakistan",
    "Palau",
    "Panama",
    "Papua New Guinea",
    "Paraguay",
    "Peru",
    "Philippines",
    "Poland",
    "Portugal",
    "Qatar",
    "Romania",
    "Russian Federation",
    "Rwanda",
    "St Kitts & Nevis",
    "St Lucia",
    "Saint Vincent & the Grenadines",
    "Samoa",
    "San Marino",
    "Sao Tome & Principe",
    "Saudi Arabia",
    "Senegal",
    "Serbia",
    "Seychelles",
    "Sierra Leone",
    "Singapore",
    "Slovakia",
    "Slovenia",
    "Solomon Islands",
    "Somalia",
    "South Africa",
    "South Sudan",
    "Spain",
    "Sri Lanka",
    "Sudan",
    "Suriname",
    "Swaziland",
    "Sweden",
    "Switzerland",
    "Syria",
    "Taiwan",
    "Tajikistan",
    "Tanzania",
    "Thailand",
    "Togo",
    "Tonga",
    "Trinidad & Tobago",
    "Tunisia",
    "Turkey",
    "Turkmenistan",
    "Tuvalu",
    "Uganda",
    "Ukraine",
    "United Arab Emirates",
    "United Kingdom",
    "United States",
    "Uruguay",
    "Uzbekistan",
    "Vanuatu",
    "Vatican City",
    "Venezuela",
    "Vietnam",
    "Yemen",
    "Zambia",
    "Zimbabwe"
)
val names = listOf(
    "Esteemed",
    "Piercer",
    "Alarm",
    "Percentage",
    "Reply",
    "Frequency",
    "Rose",
    "Corrupt",
    "Longjohns",
    "Territory",
    "Goodwill",
    "Swing",
    "Prayer",
    "Infuse",
    "Prohibited",
    "Reed",
    "Emotion",
    "Unguarded",
    "Cavalcade",
    "Dove"
)
const val NAME_MAX_SIZE = 50

val email = listOf("gmail.com", "hotmail.com", "outlook.com", "yahoo.com", "icloud.com", "aol.com", "mail.com")

val estadoJogador = listOf("ativo", "inativo", "banido")

val gameNames = listOf(
    "Martinerex" to "SCi8KGPll8",
    "IHasLegs" to "b2ZaBeNzuJ",
    "Martine Snooty Legs" to "oBuSnyUZtr",
    "Martineasaurus Rex" to "2AaJ57Mrco",
    "Martine Callous Brains" to "3AQKJDN4aE",
    "Martine English" to "wcEePgDiw6",
    "Uber Snooty Koala" to "wcEePgDiw6",
    "Disguised Koala" to "7zgv6SpYbh",
    "SnootyLegsOMG" to "69zYaIN98V",
    "CallousLegsLOL" to "KFMGS4D8w9",
    "FragileLegsOMG" to "6FdjoYdZ1v",
    "SnootyBrainsLOL" to "eSgk6NkJfC",
    "CallousBrainsOMG" to "KHSI9hpb6A",
    "FragileBrainsLMAO" to "WvKx6LoOFa",
    "Iamsnooty" to "75HToZ35Ee",
    "Iamcallous" to "urmQbAblYc",
    "Iamfragile" to "a7uOTd3mse",
    "IamMartine" to "Y6YwaSRhYO",
    "KoalaMilk" to "i7px2DVUdf",
    "Martine Fragile Koala" to "3K74tMWjwb",
    "MindOfMartine" to "rqLOoZCNaD",
    "Gamerkoala" to "ZDVbeaGcfZ",
    "The Snooty Gamer" to "dRiuhNLeeu",
    "The Callous Gamer" to "aKhOZtxIs5",
    "The Fragile Gamer" to "vVKHYWiRVM",
    "DrSnooty" to "jAN1Vc7mCu",
    "MartineLegspopper" to "fykfGxfxXA",
    "BigSnootyKoala" to "Bx9fC28WnW",
    "ItIsYeKoala" to "uhmtTXKLYh",
    "M4rt1n3" to "uhmtTXKLYh",
    "Koala Boy" to "rSCSWynQZl",
    "Koala Girl" to "zq9WrJswTd",
    "Koala Person" to "B86C76ooav",
    "Captain Snooty" to "B86C76ooav",
    "IHasBrains" to "moK0nL4aJw",
    "Total Koala" to "gf2xwNb4WF",
    "The Snooty English Dude" to "hPHmihfWqs",
    "The Gaming Koala" to "9gGkfID39L",
    "Gaming With Martine" to "bfyC8sFDL5",
    "Mr Game Koala" to "sltlDJmfsM",
    "Ms Game Koala" to "wNGWHCVWOR",
)

val estadoPartidaMultiplayer = listOf("Por iniciar", "A aguardar jogadores", "Em curso", "Terminada")

fun createWriter(fileName: String?): PrintWriter {
    return PrintWriter(fileName)
}

fun main() {
    val writer = createWriter("src/main/kotlin/scratch/insertValues.sql")
    val size = 10
    val regions = generateRegiao(size, writer)
    generateJogador(size, regions, writer)
    val games = generateJogo(size, writer)
    generateConversa(size, writer)
    generatePartida(size, games, regions, writer)
    generateCracha(writer)
    writer.close()
//    generateMensagem(size)
//    //-------------------------
//    generateCompra(size)
//    generateJoga(size)
//    generateGanha(size)
//    generateparticipa(size)
//    generateAmigo(size)
}


fun generateRegiao(size: Int, writer:PrintWriter): List<String> {
    val tableName = "regiao"
    val template = "INSERT INTO $tableName (nome) VALUES (?);"
    val usedRegions = mutableListOf<String>()
    repeat(size) {
        var newCountryName = countries.random()
        while (newCountryName in usedRegions) {
            newCountryName = countries.random()
        }
        usedRegions.add(newCountryName)
        println(template.replace("?", "'$newCountryName'"))
        writer.println(template.replace("?", "'$newCountryName'"))
    }
    return usedRegions
}

fun generateJogador(size: Int, regions: List<String> = countries, writer:PrintWriter) {
    val tableName = "jogador"
    val template = "INSERT INTO $tableName (username, email, estado, nome_regiao) VALUES (?);"
    repeat(size) {
        var nome = names.random()
        if ((1..10).random() > 5 && NAME_MAX_SIZE - nome.length > 0) {
            val adder = names.random()
            if (NAME_MAX_SIZE - nome.length > adder.length)
                nome += adder
        }
        val email = "${nome.lowercase(Locale.getDefault())}@${email.random()}"
        val regiao = regions.random()
        val estado = estadoJogador.random()
        println(template.replace("?", "'$nome', '$email', '$estado', '$regiao'"))
        writer.println(template.replace("?", "'$nome', '$email', '$estado', '$regiao'"))
    }
}

fun generateJogo(size: Int, writer:PrintWriter): List<String> {
    val tableName = "jogo"
    val template = "INSERT INTO $tableName (id, nome, url) VALUES (?);"
    val usedGames = mutableListOf<String>()
    repeat(size) {
        val (nome, id) = gameNames.random().also { usedGames.add(it.second) }
        val url = "https://${nome.filter { chr -> chr!= ' ' }}.com/?$id"
        println(template.replace("?", "'$id', '$nome', '$url'"))
        writer.println(template.replace("?", "'$id', '$nome', '$url'"))
    }
    return usedGames
}

fun generateConversa(size: Int, writer:PrintWriter) {
    val tableName = "conversa"
    val template = "INSERT INTO $tableName (nome) VALUES (?);"
    repeat(size) {
        val nome = "cvs$it"
        println(template.replace("?", "'$nome'"))
        writer.println(template.replace("?", "'$nome'"))
    }
}

fun generatePartida(size: Int, games: List<String>, regions: List<String> = countries, writer:PrintWriter) {
    val tableName = "partida"
    val template = "INSERT INTO $tableName (nr, id_jogo, data_inicio, data_fim, nome_regiao) VALUES (?);"
    val count = mutableMapOf<String, Int>()
    for (i in games) {
        count[i] = 0
    }
    repeat(size) {
        val idJogo = games.random()
        val nr = count[idJogo]!!
        count[idJogo] = nr + 1
        val dataInicio = java.sql.Date.valueOf(LocalDate.now())
        val dataFim = if ((1..10).random() > 3) dataInicio.time + (1..1000000).random() else null
        val regiao = regions.random()
        println(template.replace("?", "$nr, '$idJogo', '$dataInicio', '$dataFim', '$regiao'"))
        writer.println(template.replace("?", "$nr, '$idJogo', '$dataInicio', '$dataFim', '$regiao'"))
        if ((1..10).random() >= 5)
            generatePartidaMultijogador(idJogo, nr, writer)
        else
            generatePartidaNormal(idJogo, nr, writer)
    }
}

fun generatePartidaNormal(idJogo:String, nr: Int, writer:PrintWriter) {
    val tableName = "partida_normal"
    val template = "INSERT INTO $tableName (id_jogo, nr_partida, dificuldade, pontuacao) VALUES (?);"
    val dificuldade = (1..5).random()
    val pontuacao = (1..100000).random()
    println(template.replace("?", "$nr, '$idJogo', $dificuldade, $pontuacao"))
    writer.println(template.replace("?", "$nr, '$idJogo', $dificuldade, $pontuacao"))
}

fun generatePartidaMultijogador(idJogo:String, nr: Int, writer:PrintWriter) {
    val tableName = "partida_multijogador"
    val template = "INSERT INTO $tableName (id_jogo, nr_partida, estado, pontuacao) VALUES (?);"
    val estado = estadoPartidaMultiplayer.random()
    val pontuacao = (1..100000).random()
    println(template.replace("?", "'$idJogo', $nr, '$estado', $pontuacao"))
    writer.println(template.replace("?", "'$idJogo', $nr, '$estado', $pontuacao"))
}

fun generateCracha(writer:PrintWriter) {
    val tableName = "cracha"
    val template = "INSERT INTO $tableName (nome, id_jogo, imagem, limite_pontos) VALUES (?);"
    gameNames.forEach{
        val nome = "Cracha ${it.first}"
        val idJogo = it.second
        val imagem = "https://${it.first.filter { chr -> chr != ' ' }}.com/?${nome.filter { chr -> chr != ' ' }}"
        val limitePontos = (1..100000).random()
        println(template.replace("?", "'$nome', '$idJogo', '$imagem', $limitePontos"))
        writer.println(template.replace("?", "'$nome', '$idJogo', '$imagem', $limitePontos"))
    }
}

//fun generateInserts(tableName: String, columnsName: List<String>, columnsType: List<String>, size: Int) {
//    val columns = columnsName.joinToString(", ")
//    val values = columnsName.joinToString(", ") { "?" }
//    val insert = "INSERT INTO $tableName ($columns) VALUES ($values);"
//    println(insert)
//    for (i in 1..size) {
//        val values = columnsType.joinToString(", ") { generateValue(it) }
//        println("INSERT INTO $tableName ($columns) VALUES ($values);")
//    }
//}
//
//fun generateValue(value: String): String {
//    return when (value) {
//        "int" -> "'${(1..Int.MAX_VALUE).random()}'"
//        "varchar" -> "'${(1..100).random()}.${(1..100).random()}'"
//        "email" -> "'${(1..100).random()}.${(1..100).random()}.${(1..100).random()}'"
//        "alphanumeric" -> "'${(1..100).random()}.${(1..100).random()}.${(1..100).random()}'"
//        "url" -> "'${(1..100).random()}.${(1..100).random()}.${(1..100).random()}'"
//        "date" -> "'${(1..100).random()}.${(1..100).random()}.${(1..100).random()}'"
//        "text" -> "'${(1..100).random()}.${(1..100).random()}.${(1..100).random()}'"
//        "money" -> "'${(1..100).random()}.${(1..100).random()}.${(1..100).random()}'"
//        else -> "'${(1..100).random()}.${(1..100).random()}.${(1..100).random()}'"
//    }
//}