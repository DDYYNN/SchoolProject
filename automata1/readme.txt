## DFA index 번호는 접근순서에 따라 0,1,2,.. 식으로 numbering 됩니다.

## 추가적으로 dead 라는 것을 만들었는데 이는 NFA에서 아무 state를 가지 못할 경우, 죽은 노드로 갑니다. 이를 dead라고 표현했습니다.

## 빈 줄은 없다고 생각하고 코딩하였고, 매 입력은 “end”를 통해 끝을 나타냅니다.

## DFA에서 초기상태는 항상 0 입니다. 각 state 에는 고유번호, DFA 번호 셋, Final, Dead, zero point, one point 를 가지고 있습니다.

## 실행방법은 ./program nfa.txt string.txt output.txt 로 하시면 됩니다.


환경은 Xcode c++ 11 on Mac 입니다.
