def operand(p: bool, operand: str, q: bool = None) -> bool:
    p = int(p)
    q = int(q)
    operandToAns = {
        "+": p or q,
        ".": p and q,
        "->": 0 if p == 1 and q == 0 else 1,
        "<->": p == q,
    }
    return operandToAns.get(operand, 0)


def booleanCalculator(rpnArray: list) -> bool:
    operands = ["+", ".", "->", "<->"]
    while len(rpnArray) > 1:
        for i, item in enumerate(rpnArray):
            if item in operands:
                rpnArray[i] = str(operand(rpnArray[i-2], item, rpnArray[i-1]))
                del rpnArray[i-1]
                del rpnArray[i-2]
    return rpnArray[0]


def toRPN(inputString: str) -> list:
    precedence = {"~": 4, ".": 3, "+": 3, "->": 2, "<->": 1}

    associativity = {"~": "R", ".": "L", "+": "L", "->": "R", "<->": "R"}
    polishList = []
    operators = []
    chars = inputString.split(" ")
    for char in chars:
        if char in ['1', '0']:
            polishList.append(char)
        elif char in precedence:
            while (operators and operators[-1] != '(' and ((associativity[char] == 'L' and precedence[char] <= precedence[operators[-1]]) or (associativity[char] == 'R' and precedence[char] < precedence[operators[-1]]))):
                polishList.append(operators.pop())
            operators.append(char)
        elif char == "(":
            operators.append(char)
        elif char == ")":
            while operators and operators[-1] != "(":
                polishList.append(operators.pop())
            operators.pop()
    while operators:
        polishList.append(operators.pop())
    return polishList


if __name__ == "__main__":
    data = toRPN("( 1 . 0 ) + ( 1 . 1 ) + ( 0 . 1 )")
    print(booleanCalculator(data))