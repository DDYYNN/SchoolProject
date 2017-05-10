# Matching

## 개요
Hash-table, AVL tree 등 여러 자료 구조를 혼합해서 구현

## 지원하는 명령어
명령어는 첫 글자로 구분되며, 명령어의 종류를 나타내는 기호와 명령어의 내용은 하나의 공백 문자로 구분됩니다. 자세한 구현 방식은 5. 세부사항 부분을 참고해 주세요.

1. 데이터 입력: < (FILENAME)
패턴을 검색할 텍스트 파일을 입력합니다. 절대경로 및 상대경로를 모두 입력할 수 있습니다. 파일 이름 및 경로에는 공백이 포함되지 않는다고 가정해도 좋습니다. 새로운 텍스트 파일이 입력된 경우 이전에 입력된 데이터는 지워집니다.
2. 저장된 데이터 출력: @ (INDEX NUMBER)
입력한 번호에 해당하는 hash table의 slot에 담긴 문자열을 출력합니다. 전위 순회(preorder traversal) 방식으로 트리의 모든 노드를 출력합니다. slot이 비어 있을 경우 EMPTY를 출력합니다.
3. 패턴 검색: ? (PATTERN)
데이터 파일에서 등장하는 패턴의 위치를 모두 출력합니다. 패턴 문자열의 길이는 6 이상입니다.
패턴에 등장할 수 있는 문자의 종류에는 제한이 없습니다. 즉, 공백 문자도 패턴에 포함될 수 있습니다.
패턴의 위치는 (줄 번호, 시작 글자의 위치)의 형식으로 출력합니다. 첫 번째 줄의 첫 번째 글자는 (1, 1)입니다.
패턴이 여러 번 등장하는 경우 각 좌표는 공백으로 구분합니다. 마지막 좌표 뒤에는 공백을 출력하지 않습니다.
패턴이 검색되지 않는 경우 (0, 0)을 출력합니다.
4. 종료: QUIT
프로그램을 종료합니다.

## 입력
입력 데이터는 한 줄에 문자열이 하나씩 있는 파일로 받습니다. 모든 줄은 6글자 이상으로 이루어져 있습니다.

이 예제는 5줄이므로 string 수는 5개입니다.

```
this is a boy. hello, boy.
it is more important to avoid using a bad data structure.
i am a boyboy. boys be ambitious!
boyboyoboyboyboy
more important to avoid it is more important to data
```

## 세부사항
전체 string의 개수가 n일 때, string의 집합을 S = { S1 , S2 , … , Sn} 이라고 하자. S로 아래에 제시된 hash table과 AVL tree, linked list를 구성한다.
string Si (i=1,2, …,n)의 길이가 m일 때, Si에 대해서 길이 k인 substring Si[1..k], Si[2..k+1], …, Si[m-k+1..m] 이 존재한다. (Si[x..y]는 index가 x부터 y까지인 substring, 1 ≤ x ≤ y ≤ m)
길이 k인 모든 substring Si[j..j+k-1](1 ≤ i ≤ n, 1 ≤ j ≤ m-k+1, m: Si의 길이) 에 대하여 아래의 과정을 수행한다.

1. 각 substring을 hashing한다.
    1. hash function: (k character들의 ASCII code들의 합) mod 100
    2. table의 크기는 collision을 유발하기 위해 비현실적이지만 100으로 한다.
2. hash table의 각 slot은 AVL tree로 구현한다. 서로 다른 substring이지만 hashing값이 같으면 collision이 일어나므로 이들은 AVL tree로 구별한다.
3. AVL tree에서 노드의 추가로 인해 높이의 불균형이 발생하였을 경우, 해당하는 노드 중 새로 추가된 노드에서 가장 가까운 조상을 기준으로 회전 연산을 적용한다.
4. AVL tree의 각 node는 linked list로 구현한다. 하나의 substring이 S상에서 여러 번 등장할 수 있다. 이러한 경우 AVL tree의 해당 node에서 linked list로 연결, 관리한다.
5. string이 Si[j..j+k-1]일 때, linked list의 node는 (i, j) 값을 갖는다.
6. 예를 들어, S3 = "i am a boyboy. boys be ambitious!", S4= "boyboyoboyboyboy", k = 6이고, H("boyboy")에 의해 결정된 hash table 상의 index가 ("boyboy"의 ASCII값의 합) mod 100이면, substring S3[8..13], S4[1..6], S4[8..13], S4[11..16]에 의해 아래 그림과 같은 자료구조가 생성된다.
7. S상에서 길이 k인 모든 substring에 대해 그림과 같이 구성한다.
본 숙제에서 k = 6으로 잡는다.
string의 길이가 6 미만인 경우는 구현의 단순성을 위해 고려하지 않는다.
모든 실제 문제에서 사용되는 data string, pattern string의 길이는 6이상이고, 이를 처리하기 위한 자료구조에서 검색의 key가 되는 substring의 길이는 6으로 고정한다.
즉, 길이가 6인 substring들에 대한 저장된 정보를 바탕으로 길이가 6 이상인 패턴 스트링을 처리하는 것이다.


## 예제 입출력
```
$ java Matching                             <- 프로그램 실행
< data.txt                                  <- 이렇게 입력
? boyboy                                    <- 이렇게 입력하면
(3, 8) (4, 1) (4, 8) (4, 11)                <- 이렇게 출력한다.
? important to                              <- 이렇게 입력하면
(2, 12) (5, 6) (5, 36)                      <- 이렇게 출력한다.
? algorithm                                 <- 이렇게 입력하면
(0, 0)                                      <- 이렇게 출력한다.
@ 60                                        <- 이렇게 입력하면
portan boyboy oyboyb yboyob yboybo yoboyb   <- 이렇게 출력한다.
@ 73                                        <- 이렇게 입력하면
oyboyo ata st re imp oyoboy                 <- 이렇게 출력한다.
@ 0                                         <- 이렇게 입력하면
EMPTY                                       <- 이렇게 출력한다.
QUIT                                        <- 이렇게 입력하면
$                                           <- 종료한다.
```



