package tictactoe

import kotlin.math.abs

fun main() {
    val board = createBoard()
    printBoard(board)
    var xMove = true
    do {
        processMove(board, xMove)
        printBoard(board)
        xMove = !xMove
    } while (findWinner(board) == 'G')

    when (val winner = findWinner(board)) {
        'D' -> print("Draw")
        'G' -> print("Game not finished")
        else -> print("$winner wins")
    }
}


fun createBoard(): MutableList<MutableList<Char>> {
    return mutableListOf(
        MutableList(3) { ' ' },
        MutableList(3) { ' ' },
        MutableList(3) { ' ' },
    )
}


fun processInput(board: MutableList<MutableList<Char>>, input: String): MutableList<MutableList<Char>> {
    for (i in 0..board.lastIndex) {
        for (j in 0..board[i].lastIndex) {
            board[i][j] = input[3 * i + j]
        }
    }
    return board
}


fun printBoard(board: MutableList<MutableList<Char>>) {
    val line = "---------"
    println(line)
    for (row in board) {
        println(row.joinToString(prefix = "| ", separator = " ", postfix = " |"))
    }
    println(line)
}


fun countElements(board: MutableList<MutableList<Char>>, element: Char): Int {
    var count = 0
    for (row in board) {
        for (elem in row) {
            if (elem == element) {
                count++
            }
        }
    }
    return count
}


fun validateBoard(board: MutableList<MutableList<Char>>): Boolean {
    val xCount = countElements(board, 'X')
    val oCount = countElements(board, 'O')

    if (abs(xCount - oCount) > 1) {
        return false
    } else if (board[0][0] == board[1][1] && board[1][1] == board[2][2] ||
        board[0][2] == board[1][1] && board[1][1] == board[2][0]
    ) {
        return true
    } else {
        for (i in 0..board.lastIndex) {
            for (j in i + 1..board.lastIndex) {
                if (board[i][0] != '_' && board[j][0] != '_' && board[i][0] == board[i][1] && board[i][1] == board[i][2]
                    && board[j][0] == board[j][1] && board[j][1] == board[j][2]
                ) {
                    return false
                } else if (board[0][i] != '_' && board[0][j] != '_' &&
                    board[0][i] == board[1][i] && board[1][i] == board[2][i] &&
                    board[0][j] == board[1][j] && board[1][j] == board[2][j]
                ) {
                    return false
                }
            }
        }
    }
    return true
}


fun findWinner(board: MutableList<MutableList<Char>>): Char {
    if (board[1][1] != ' ' && (board[0][0] == board[1][1] && board[1][1] == board[2][2] ||
                board[0][2] == board[1][1] && board[1][1] == board[2][0])
    ) {
        return board[1][1]
    }

    for (row in board) {
        if (row[0] != ' ' && row[0] == row[1] && row[1] == row[2]) {
            return row[0]
        }
    }

    for (i in board.indices) {
        if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
            return board[0][i]
        }
    }

    for (row in board) {
        if (row.contains(' ')) {
            return 'G'
        }
    }
    return 'D'
}


fun processMove(board: MutableList<MutableList<Char>>, xMove: Boolean) {
    var move: String
    do {
        print("Enter the coordinates: ")
        move = readln()
    } while (!validateMove(board, move))

    val (y, x) = move.split(' ').map { it.toInt() - 1 }
    if (xMove) board[y][x] = 'X' else board[y][x] = 'O'
}


fun validateMove(board: MutableList<MutableList<Char>>, move: String): Boolean {
    val y: Int
    val x: Int
    try {
        y = move.split(' ').map { it.toInt() - 1 }[0]
        x = move.split(' ').map { it.toInt() - 1 }[1]
    } catch (e: NumberFormatException) {
        println("You should enter numbers!")
        return false
    }

    if (y !in 0..2 || x !in 0..2) {
        println("Coordinates should be from 1 to 3!")
        return false
    }

    if (board[y][x] != ' ') {
        println("This cell is occupied! Choose another one!")
        return false
    }
    return true
}
