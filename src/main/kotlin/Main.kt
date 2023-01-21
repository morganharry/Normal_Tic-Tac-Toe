fun chooseType (): String {
    var status = ""
    println ("Please, choose type of the game.\n--1-player vs player\n--2-player vs computer\nEnter 1 or 2")
    while (status == "") {
        val read = readln()
        when (read){
            "1" -> status = "player vs player"
            "2" -> status = "player vs computer"
            else -> println ("Please, enter: 1 or 2:")
        }
    }
    return status
}

fun printField (field: MutableList<MutableList<Char>>) {
    println ("\n---A-B-C---")
    for (f in field.indices) {
        println("${f+1}| ${field[f].joinToString(" ")} |${f+1}")
    }
    println ("---A-B-C---\n")
}

fun checkFirst ():Char {
    var sign = ' '
    while (sign == ' ') {
        println("Who is first - X or O?")
        val first = readln().uppercase()
        println()
        when (first) {
            "X" -> sign = 'X'
            "O" -> sign = 'O'
            else -> println("Please, enter the sign: X or O\n")
        }
    }
    return sign
}

fun chooseFirst ():String {
    var playerFirst = " "
    while (playerFirst == " ") {
        println("Whose move is first - player or computer?\n--1-player\n--2-computer")
        var read = readln()
        println()
        when (read) {
            "1" -> playerFirst = "player"
            "2" -> playerFirst = "computer"
            else -> println("Please, enter the sign: 1 or 2\n")
        }
    }
    return playerFirst
}

fun changeSign (sign:Char):Char {
    if (sign == 'X') return 'O'
    else return 'X'
}

fun changePlayer (playerFirst:String):String {
    if (playerFirst == "player") return "computer"
    else return "player"
}


fun filterMove (coordinates:String) : MutableList<Int> {
    val listMoveDigit = mutableListOf<Char>()
    val listMoveLetter = mutableListOf<Char>()
    val listMove= mutableListOf<Int>()
    for (i in coordinates.indices) {
        when {
            coordinates[i].isDigit()-> listMoveDigit.add(coordinates[i])
            coordinates[i].isLetter()-> listMoveLetter.add(coordinates[i])
        }
    }
    when {
        listMoveDigit.size == 0 || listMoveLetter.size == 0 -> {
            println("You wrote the wrong numbers of characters. Please, try again.")
            listMove.add(3)
        }
        listMoveDigit.size > 1 ||listMoveLetter.size > 1-> {
            println("You wrote the wrong numbers of characters. Please, try again.")
            listMove.add(3)
        }
        listMoveDigit.first() !in ('1'..'3') || listMoveLetter.first().uppercaseChar() !in ('A'..'C') -> {
            println("There is no letter (A, B, C) or number (1, 2, 3). Please, try again.")
            listMove.add(3)
        }
        else -> {
            listMove.add(convertionMove(listMoveDigit, 'y'))
            listMove.add(convertionMove(listMoveLetter, 'x'))
            println("Your move: ${listMoveLetter.joinToString("")}-${listMoveDigit.joinToString("")}")
        }
    }
    return listMove
}

fun convertionMove (listMove : MutableList<Char>, axis: Char): Int {
    var x = 0
    var y = 0
    for (i in listMove.indices) {
        when (listMove[i].uppercaseChar()) {
            'A'-> x = 0
            'B'-> x = 1
            'C'-> x = 2
            '1'-> y = 0
            '2'-> y = 1
            '3'-> y = 2
        }
    }
    if (axis == 'x') return x
    else return y
}

fun checkFree (listMove: MutableList<Int>, field : MutableList<MutableList<Char>>): String {
    if (field[listMove[0]][listMove[1]] == '_') return "good"
    else {
        println ("This cell is not free. Try again.\n")
        return "bad"
    }

}

fun checkWin (sign:Char, field: MutableList<MutableList<Char>>): String {
    var status = "move"
    when {
        (field[0][0] == field[0][1] && field[0][1] == field[0][2] && sign == field[0][2]) ||
                (field[1][0] == field[1][1] && field[1][1] == field[1][2] && sign == field[1][2]) ||
                (field[2][0] == field[2][1] && field[2][1] == field[2][2] && sign == field[2][2]) ||
                (field[0][0] == field[1][0] && field[1][0] == field[2][0] && sign == field[2][0]) ||
                (field[0][1] == field[1][1] && field[1][1] == field[2][1] && sign == field[2][1]) ||
                (field[0][2] == field[1][2] && field[1][2] == field[2][2] && sign == field[2][2]) ||
                (field[0][0] == field[1][1] && field[1][1] == field[2][2] && sign == field[2][2]) ||
                (field[0][2] == field[1][1] && field[1][1] == field[2][0] && sign == field[2][0])-> {
            status = "end"
            println ("Win! ${sign} - the winner!\n")
        }
    }
    return status
}

fun checkDraw (field: MutableList<MutableList<Char>>): String {
    var status = "move"
    if (('_' !in field[0]) && ('_' !in field[1]) && ('_' !in field[2])) {
        status = "end"
        println ("Draw. Good game!\n")
    }
    return status
}

fun endOfGame (): String {
    var status = "next"
    println ("Choose next action: start new game or exit.\n--1-start\n--2-exit")
    while (status == "next") {
        val read = readln()
        when (read){
            "1" -> status = "start"
            "2" -> status = "exit"
            else -> println ("Please, enter:\\n--1-start\\n--2-exit\"")
        }
    }
    return status
}

fun playerMove (sign:Char, field: MutableList<MutableList<Char>>) : MutableList<Int> {
    var statusMove = "bad"
    var listMove = mutableListOf<Int>()
    do {
        println("${sign}'s move! Please, enter the coordinates in the format letter (A, B, C) and number (1, 2, 3):")
        var coordinates = readln()
        println()
        listMove = filterMove(coordinates) // (3) or (1,1)
        if (listMove.first() != 3) statusMove = checkFree(listMove, field)
    } while (statusMove != "good")
    return listMove
}

fun typePvsP (): String {
    var sign = checkFirst()
    var listMove = mutableListOf<Int>()
    var statusGame = "move"
    var field = MutableList(3) { MutableList<Char>(3) { '_' } }
    printField(field)
    while (statusGame == "move") {
        listMove = playerMove (sign,field)
        field[listMove[0]][listMove[1]] = sign
        printField(field)
        statusGame = checkWin(sign, field)
        if (statusGame != "end") statusGame = checkDraw(field)
        if (statusGame == "move") {
            sign = changeSign(sign)
        }
    }
    return statusGame
}


fun main() {
    var statusGame = "start"
    var typeOfGame = " "
    println("Welcome to the game \"Tic-tac-Toe\"")
    while (statusGame != "exit") {
        when (statusGame) {
            "start" -> {
                typeOfGame = chooseType()
                println("You choose type of the game - ${typeOfGame}\n")
                statusGame = "move"
            }
            "move" -> {
                when (typeOfGame) {
                    "player vs player" -> statusGame = typePvsP()
                    "player vs computer"-> statusGame = typePvsC()
                }
            }
            "end" -> {
                statusGame = endOfGame ()
            }
        }
    }
}

fun typePvsC (): String {
    var sign = checkFirst()
    var playerFirst = chooseFirst()
    var listMove = mutableListOf<Int>()
    var statusGame = "move"
    var statusMove = "bad"
    var x = 0
    var y = 0
    var field = MutableList(3) { MutableList<Char>(3) { '_' } }
    printField(field)
    while (statusGame == "move") {
        when (playerFirst) {
            "player" -> {
                listMove = playerMove(sign, field)
                field[listMove[0]][listMove[1]]  = sign
                printField(field)
                statusGame = checkWin(sign, field)
                if (statusGame != "end") statusGame = checkDraw(field)
                if (statusGame == "move") {
                    sign = changeSign(sign)
                    playerFirst = changePlayer(playerFirst)
                }
            }
            "computer" -> {
                listMove = computerMove(sign, field)
                field[listMove[0]][listMove[1]] = sign
                printField(field)
                statusGame = checkWin(sign, field)
                if (statusGame != "end") statusGame = checkDraw(field)
                if (statusGame == "move") {
                    sign = changeSign(sign)
                    playerFirst = changePlayer(playerFirst)
                }
            }
        }
    }
    return statusGame
}

fun computerMove (sign:Char, field: MutableList<MutableList<Char>>) : MutableList<Int> {
    println("${sign}'s move! Computer thinks - where can I move? Hmm...")
    var statusMove = "bad"
    var listMove = mutableListOf<Int>()
    do {
        if (field[1][1] == '_') {
            listMove = mutableListOf (1, 1)

        }
        else listMove = generatorOfMove (field)
        statusMove = checkFree(listMove, field)
        } while (statusMove != "good")
    return listMove
}

fun generatorOfMove (field: MutableList<MutableList<Char>>) : MutableList<Int> {
    var listMove = mutableListOf<Int>()
    when {
        field[1][1] == field[0][0] && field[2][2] == '_'-> listMove = mutableListOf (2, 2)
        field[1][1] == field[0][1] && field[2][1] == '_'-> listMove = mutableListOf (2, 1)
        field[1][1] == field[0][2] && field[2][0] == '_'-> listMove = mutableListOf (2, 0)
        field[1][1] == field[1][2] && field[1][0] == '_'-> listMove = mutableListOf (1, 0)
        field[1][1] == field[2][2] && field[0][0] == '_'-> listMove = mutableListOf (0, 0)
        field[1][1] == field[2][1] && field[0][1] == '_'-> listMove = mutableListOf (0, 1)
        field[1][1] == field[2][0] && field[0][2] == '_'-> listMove = mutableListOf (0, 2)
        field[1][1] == field[1][0] && field[1][2] == '_'-> listMove = mutableListOf (1, 2)
        field[0][1] == field[0][0] && field[0][2] == '_'-> listMove = mutableListOf (0, 2)
        field[0][1] == field[0][2] && field[0][0] == '_'-> listMove = mutableListOf (0, 0)
        field[2][1] == field[2][0] && field[2][2] == '_'-> listMove = mutableListOf (2, 2)
        field[2][1] == field[2][2] && field[2][0] == '_'-> listMove = mutableListOf (2, 0)
        field[1][0] == field[0][0] && field[2][0] == '_'-> listMove = mutableListOf (2, 0)
        field[1][0] == field[2][0] && field[0][0] == '_'-> listMove = mutableListOf (0, 0)
        field[1][2] == field[0][2] && field[2][2] == '_'-> listMove = mutableListOf (2, 2)
        field[1][2] == field[2][2] && field[0][2] == '_'-> listMove = mutableListOf (0, 2)
        field[0][0] == field[0][2] && field[0][1] == '_'-> listMove = mutableListOf (0, 1)
        field[0][2] == field[2][2] && field[1][2] == '_'-> listMove = mutableListOf (1, 2)
        field[2][2] == field[2][0] && field[2][1] == '_'-> listMove = mutableListOf (2, 1)
        field[2][0] == field[0][0] && field[1][0] == '_'-> listMove = mutableListOf (1, 0)
        field[0][0] == field[2][2] && field[1][1] == '_'-> listMove = mutableListOf (1, 1)
        field[2][0] == field[0][2] && field[1][1] == '_'-> listMove = mutableListOf (1, 1)
        else -> {
            when {
                field[0].indexOf('_') != -1 -> listMove = mutableListOf (0, field[0].indexOf('_'))
                field[1].indexOf('_') != -1 -> listMove = mutableListOf (1, field[1].indexOf('_'))
                field[2].indexOf('_') != -1 -> listMove = mutableListOf (2, field[2].indexOf('_'))
            }
        }
    }
    println("Computer move: ${listMove.joinToString("-")}")
    return listMove
}
/*
---A-B-C---
1| O _ _ |1
2| O X _ |2
3| X O X |3
---A-B-C---
*/