//
//  main.cpp
//  automata3
//
//  Created by jungrae on 2016. 11. 24..
//  Copyright © 2016년 jungrae. All rights reserved.
//

#include <iostream>
#include <list>
#include <map>
#include <string>
#include <vector>
#include <sstream>
#include <fstream>
using namespace std;

class A{
public:
    A(){}
    A(int a,char b,char c):next_state(a),new_char(b),dir(c){}
    char new_char,dir;
    int next_state;
};
class State{
public:
    map<char,A> rule;
};
vector<State*> state;
vector<char> cand;
list<char> tape;

int n,m,type_tape;

int main(int argc, const char * argv[]) {
    if (argc!=4) return 0;
    ifstream ifile1(argv[1]);
    ifile1 >> m >> n >> type_tape;
    for (int i=0;i<m;i++){
        char a;
        ifile1 >> a;
        cand.push_back(a);
    }
    ifile1.ignore(256,'\n');
    for (int i=0;i<=n+1;i++) state.push_back(new State());
    string main_str;
    while(getline(ifile1,main_str)){
        vector<string> str(10); int cnt=0;
        istringstream iss(main_str);
        while(getline(iss,str[++cnt],' ')) str.push_back("");
        State* now = state[stoi(str[1])];
        for (int i=0;i<cand.size();i++){
            string temp = str[i+2].substr(1,str[i+2].length()-1);
            istringstream iss2(temp);
            string str2[5]; int cnt2=0;
            while(getline(iss2,str2[++cnt2],','));
            now->rule[cand[i]] = *(new A(stoi(str2[1]),str2[2][0],str2[3][0]));
        }
    }
    ifstream ifile2(argv[2]);
    ofstream outfile(argv[3]);
    while(getline(ifile2,main_str)){
        tape.clear();
        tape.push_back('#');
        for (int i=0;i<main_str.length();i++) tape.push_back(main_str[i]);
        list<char>::iterator it = tape.begin();
        int now=0;
        while(1){
            if (now==n || now==n+1) break;
            /*
            for (auto it2=tape.begin();it2!=tape.end();it2++) cout << *it2;
            cout << endl;
            for (auto it2=tape.begin();it2!=tape.end();it2++){
                if (it2==it) cout << "_";
                else cout << " ";
            }
            cout << endl;
             */
            A temp = state[now]->rule[*it];
            *it = temp.new_char;
            now = temp.next_state;
            if (temp.dir=='L'){
                if (it==tape.begin()) tape.push_front('#');
                it--;
            }
            else if (temp.dir=='R'){
                it++;
                if (it==tape.end()){
                    tape.push_back('#');
                    it--;
                }
            }
        }
        if (type_tape==0){
            if (now==n) outfile << "n\n";
            else outfile << "y\n";
        }
        for (it=tape.begin();it!=tape.end();it++) if (*it!='#') outfile << *it;
        outfile << "\n\n";
    }
    return 0;
}
