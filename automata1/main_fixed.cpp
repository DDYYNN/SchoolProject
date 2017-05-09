//
//  main.cpp
//  automata
//
//  Created by jungrae on 2016. 10. 10..
//  Copyright © 2016년 jungrae. All rights reserved.
//

#include <iostream>
#include <map>
#include <vector>
#include <sstream>
#include <set>
#include <fstream>
#define M 10000000
using namespace std;

map<set<int>, int> m;

class FA{
public:
    int n;
    bool Final[M],dead[M];
    vector<int> v[M][3];
}DFA, NFA;

set<int> set_DFA[M];

void dfs(int here, set<int> &ss) {
    if (ss.find(here)!=ss.end()) return;
    ss.insert(here);
    for (auto there : NFA.v[here][2]) dfs(there, ss);
}

int change(set<int> &ss) {
    if (m.find(ss) == m.end()) {
        m[ss] = DFA.n; set_DFA[DFA.n]=ss;
        DFA.n++;
    }
    else return m[ss];
    
    int here = m[ss];
    
    if (ss.size()==0) DFA.dead[here]=1;
    
    for (auto i : ss) if (NFA.Final[i]) DFA.Final[here] = 1;
    
    set<int> s0;
    for (auto i : ss) {
        for (auto j : NFA.v[i][0]) {
            dfs(j, s0);
        }
    }
    DFA.v[here][0].push_back(change(s0));
    
    set<int> s1;
    for (auto i : ss) {
        for (auto j : NFA.v[i][1]) {
            dfs(j, s1);
        }
    }
    DFA.v[here][1].push_back(change(s1));
    
    return here;
}

int main(int argc, const char * argv[]) {
    string main_str, str[10];
    int cnt;
    
    if (argc<4) return 0;
    
    freopen(argv[3],"w",stdout);
    ifstream ifile(argv[1]);
    
    while (getline(ifile, main_str)) {
        if (main_str.compare("end") == 0) break;
        
        istringstream iss(main_str);
        cnt = 0;
        while (iss) iss >> str[++cnt];
        
        NFA.n++;
        int here = atoi(str[1].c_str());
        if (str[2].compare("1") == 0) NFA.Final[here] = 1;
        
        string tt;
        if (str[3].compare("-") != 0) {
            istringstream r1(str[3]);
            while (getline(r1, tt, ',')) NFA.v[here][0].push_back(atoi(tt.c_str()));
        }
        if (str[4].compare("-") != 0) {
            istringstream r2(str[4]);
            while (getline(r2, tt, ',')) NFA.v[here][1].push_back(atoi(tt.c_str()));
        }
        if (str[5].compare("-") != 0) {
            istringstream r3(str[5]);
            while (getline(r3, tt, ',')) NFA.v[here][2].push_back(atoi(tt.c_str()));
        }
    }
    set<int> ss; ss.insert(0); dfs(0,ss);
    change(ss);
    cout << "No.\nElements\nFinal\tDead\n0:\t1:\n";
    for (int i = 0; i<DFA.n; i++) {
        cout << i <<"\n";
        cout << "{ ";
        for (auto j : set_DFA[i]) cout << j << " ";
        cout << "}\n";
        cout << "Final:" << DFA.Final[i] << "\tDead:" << DFA.dead[i] << "\n";
        cout << "0: " << DFA.v[i][0][0] << "\t1: " << DFA.v[i][1][0] << "\n\n";
    }
    ifstream ifile2 (argv[2]);
    while(getline(ifile2,main_str)){
        if (main_str.compare("end")==0) break;
        
        cout << "DFA : ";
        int now=0;
        for (int i=0;i<main_str.length();i++) now=DFA.v[now][main_str[i]-'0'][0];
        if (DFA.Final[now]) cout << "yes\n";
        else cout << "no\n";
        
        cout << "NFA : ";
        set<int> now0,now1; dfs(0,now0);
        for (int i=0;i<main_str.length();i++){
            now1.clear();
            for (auto j : now0){
                for (auto r : NFA.v[j][main_str[i]-'0']) dfs(r,now1);
            }
            now0 = now1;
        }
        bool flag=false;
        for (auto j : now0) if (NFA.Final[j]) flag=true;
        if (flag) cout << "yes\n\n";
        else cout << "no\n\n";
    }
    return 0;
}

