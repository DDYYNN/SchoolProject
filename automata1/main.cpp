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
using namespace std;

struct node{
    int num;
    node *zero,*one;
    bool Final,Dead;
};
struct node2{
    vector<int> zero,one,epsilon;
    bool Final;
};

node *DFA_st;
map<set<int>, node> DFA;
node2 NFA[40];

int DFA_N,NFA_N;

void dfs(int here, set<int> &ss) {
    if (ss.find(here)!=ss.end()) return;
    ss.insert(here);
    for (auto there : NFA[here].epsilon) dfs(there, ss);
}

node* change(set<int> &ss) {
    if (DFA.find(ss)==DFA.end()){
        DFA[ss] = node(); DFA[ss].num=DFA_N++;
    }
    else return &DFA[ss];
    
    node &here = DFA[ss];
    
    if (ss.size()==0) here.Dead=1;
    
    for (auto i : ss) if (NFA[i].Final) here.Final=1;
    
    set<int> s0;
    for (auto i : ss) {
        for (auto j : NFA[i].zero) {
            dfs(j, s0);
        }
    }
    here.zero = change(s0);
    
    set<int> s1;
    for (auto i : ss) {
        for (auto j : NFA[i].one) {
            dfs(j, s1);
        }
    }
    here.one = change(s1);
    
    return &here;
}

int main(int argc, const char * argv[]) {
    string main_str, str[10];
    int cnt;
    
    if (argc<4) return 0;
    
    freopen(argv[3],"w",stdout);
    ifstream ifile(argv[1]);
    //    while(getline(cin,main_str)){
    while (getline(ifile, main_str)) {
        if (main_str.compare("end") == 0) break;
        
        istringstream iss(main_str);
        cnt = 0;
        while (iss) iss >> str[++cnt];
        
        NFA_N++;
        int here = atoi(str[1].c_str());
        
        if (str[2].compare("1") == 0) NFA[here].Final = 1;
        
        string tt;
        if (str[3].compare("-") != 0) {
            istringstream r1(str[3]);
            while (getline(r1, tt, ',')) NFA[here].zero.push_back(atoi(tt.c_str()));
        }
        if (str[4].compare("-") != 0) {
            istringstream r2(str[4]);
            while (getline(r2, tt, ',')) NFA[here].one.push_back(atoi(tt.c_str()));
        }
        if (str[5].compare("-") != 0) {
            istringstream r3(str[5]);
            while (getline(r3, tt, ',')) NFA[here].epsilon.push_back(atoi(tt.c_str()));
        }
    }
    set<int> ss; dfs(0,ss);
    DFA_st=change(ss);
    cout << "No.\nElements\nFinal\tDead\n0:\t1:\n";
    
    for (auto e : DFA){
        cout << e.second.num << "\n{ ";
        for (auto i : e.first) cout << i << " ";
        cout << "}\nFinal: " << e.second.Final << "\tDead: " << e.second.Dead << "\n0: " << e.second.zero->num << "\t1: " << e.second.one->num << "\n\n";
    }
    if (DFA_st==nullptr) return 0;
    
    
    ifstream ifile2 (argv[2]);
    //    while(getline(cin,main_str)){
    while(getline(ifile2,main_str)){
        if (main_str.compare("end")==0) break;
        
        cout << "DFA : ";
        node* here = DFA_st;
        for (int i=0;i<main_str.length();i++){
            if (main_str[i]=='0') here = here->zero;
            else here = here->one;
        }
        if (here->Final) cout << "yes\n";
        else cout << "no\n";
        
        cout << "NFA : ";
        set<int> now0,now1; dfs(0,now0);
        for (int i=0;i<main_str.length();i++){
            now1.clear();
            for (auto j : now0){
                if (main_str[i]=='0'){
                    for (auto r : NFA[j].zero) dfs(r,now1);
                }
                else{
                    for (auto r : NFA[j].one) dfs(r,now1);
                }
            }
            now0 = now1;
        }
        bool flag=false;
        for (auto j : now0) if (NFA[j].Final) flag=true;
        if (flag) cout << "yes\n\n";
        else cout << "no\n\n";
    }
    return 0;
}
