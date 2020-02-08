import os
import random

def get_random_excluding(maximum, exclusion_list):
    rand = random.randint(0, maximum)
    return rand if not rand in exclusion_list else get_random_excluding(maximum, exclusion_list)

def generate_boards(BOARD_SIZE):
    boards = []
    for i in range(10):
        board = []
        maximum = (BOARD_SIZE * BOARD_SIZE) - 1;
        for i in range(maximum + 1):
            board.append(get_random_excluding(maximum, board))
        boards.append(board)
    return boards

def output_boards_to_file(boards, filename):
    output = ''
    for board in boards:
        for row in range(BOARD_SIZE):
            for col in range(BOARD_SIZE):
                output += str(board[(BOARD_SIZE * row) + col]) + ' '
            output += '\n'
        output += '\n'
    with open(filename, 'w+') as writer:
        writer.write(output)

if __name__ == '__main__':
    BOARD_SIZE = 3
    boards = generate_boards(BOARD_SIZE)
    output_boards_to_file(boards, 'random_boards.txt')
