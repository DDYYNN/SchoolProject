//
//  main.cpp
//  automata2
//
//  Created by jungrae on 2016. 11. 16..
//  Copyright © 2016년 jungrae. All rights reserved.
//

#include <iostream>
#include <vector>
#include <algorithm>
#include <map>
#include <string>
#include <set>
#include <fstream>
using namespace std;

class Chomsky_variable{ // 각 변수가 가지는 생성규칙
public:
    set<string> rule;
};
class Chomsky_rule{ // 각 생성규칙과 대응되는 변수 집합
    // 각 변수는 자기자신을 가지는 생성규칙과 대응. A:A
public:
    set<char> variable;
};

map<char,Chomsky_variable*> m; // variable
map<string,Chomsky_rule*> db; // rule

vector<string> save;

bool chk[55];

char get(){ // 아직 사용하지 않은 변수 생성
    for (int i=0;i<26;i++){
        if (m.find('A'+i)==m.end()){
            return 'A'+i;
        }
    }
    for (int i=0;i<26;i++){
        if (m.find('a'+i)==m.end()){
            return 'a'+i;
        }
    }
    return 0;
}
void Set_Chomsky(char a,string str){
    if (str.length()==1){
        if (m.find(str[0])==m.end() && db.find(str)==db.end()){ // 미정알파벳
            char new_variable = get();
            m[new_variable] = new Chomsky_variable();
            m[new_variable]->rule.insert(str);
            db[str] = new Chomsky_rule();
            db[str]->variable.insert(new_variable);
            
            string temp; temp+=new_variable;
            m[a]->rule.insert(temp);
        }
        else if (m.find(str[0])==m.end()){ // 지정 알파벳
            string temp; temp+=*(db[str]->variable.begin());
            m[a]->rule.insert(temp);
        }
        else{
            m[a]->rule.insert(str);
        }
    }
    else{
        char le,ri;
        
        string temp;
        temp+=str[0];
        if (m.find(str[0])==m.end() && db.find(temp)==db.end()){ // 미정 알파벳
            char new_variable = get();
            m[new_variable] = new Chomsky_variable();
            m[new_variable]->rule.insert(temp);
            db[temp] = new Chomsky_rule();
            db[temp]->variable.insert(new_variable);
            le = new_variable;
        }
        else if (m.find(str[0])==m.end()){ // 지정 알파벳
            le = *(db[temp]->variable.begin());
        }
        else le = str[0];
        
        temp=str.substr(1,str.length());
        if (str.length()==2 && m.find(str[1])==m.end() && db.find(temp)==db.end()){
            char new_variable = get();
            m[new_variable] = new Chomsky_variable();
            m[new_variable]->rule.insert(temp);
            db[temp] = new Chomsky_rule();
            db[temp]->variable.insert(new_variable);
            ri = new_variable;
        }
        else if (str.length()==2 && m.find(str[1])!=m.end()) ri = str[1];
        else if (db.find(temp)==db.end()){ // 미정 스트링
            char new_variable = get();
            m[new_variable] = new Chomsky_variable();
            Set_Chomsky(new_variable, temp); // 새로운 변수에 대한 생성규칙 생성.
            db[temp] = new Chomsky_rule();
            db[temp]->variable.insert(new_variable);
            ri = new_variable;
        }
        else ri = *(db[temp]->variable.begin()); // 지정 스트링 or 지정 알파벳
        
        string str2; str2+=le; str2+=ri;
        m[a]->rule.insert(str2);
    }
}
void update(char a,set<string> &new_rule){
    int now;
    if (isupper(a)) now=a-'A';
    else now=a-'a'+26;
    
    if (chk[now]) return;
    chk[now]=1;
    for (auto str : m[a]->rule){
        if (str.length()==1 && (isupper(str[0]) || islower(str[0]))) update(str[0],new_rule);
        else new_rule.insert(str);
    }
}
void print(){
    for (int i=0;i<26;i++){
        char a = 'A'+i;
        if (m.find(a)!=m.end()){
            for (auto str : m[a]->rule) cout << a << ":" << str << '\n';
        }
    }
    for (int i=0;i<26;i++){
        char a = 'a'+i;
        if (m.find(a)!=m.end()){
            for (auto str : m[a]->rule) cout << a << ":" << str << '\n';
        }
    }
}

char root=0;

int main(int argc,char *argv[]) {
    if (argc!=4) return 0;
    
    freopen(argv[3],"w",stdout);
    
    string main_str;
    ifstream ifile(argv[1]);
    
    while (getline(ifile, main_str)) {
        if (!main_str.compare("end")) break;
        
        if (!root) root = main_str[0];
        
        save.push_back(main_str);
        m[main_str[0]] = new Chomsky_variable();
    }
    for (auto str : save){
        Set_Chomsky(str[0],str.substr(2,str.length()));
    }
    for (int i=0;i<26;i++){
        if (m.find('A'+i)!=m.end()){
            for (int j=0;j<52;j++) chk[j]=0;
            set<string> new_rule;
            update('A'+i,new_rule);
            m['A'+i]->rule = new_rule;
            for (auto str : m['A'+i]->rule){
                if (db.find(str)==db.end()) db[str] = new Chomsky_rule();
                db[str]->variable.insert('A'+i);
            }
        }
        if (m.find('a'+i)!=m.end()){
            for (int j=0;j<52;j++) chk[j]=0;
            set<string> new_rule;
            update('a'+i,new_rule);
            m['a'+i]->rule = new_rule;
            for (auto str : m['a'+i]->rule){
                if (db.find(str)==db.end()) db[str] = new Chomsky_rule();
                db[str]->variable.insert('a'+i);
            }
        }
    }
    print();
    cout << '\n';
    
    set<char> d[502][502];
    
    ifstream ifile2(argv[2]);
    while(getline(ifile2,main_str)){
        if (!main_str.compare("end")) break;
        
        cout << main_str << ": ";
        for (int i=0;i<main_str.length();i++) for (int j=0;j<main_str.length();j++) d[i][j].clear();
        for (int i=0;i<main_str.length();i++){
            string str; str+=main_str[i];
            if (db.find(str)!=db.end()) d[i][i] = db[str]->variable;
        }
        for (int w=1;w<main_str.length();w++){
            for (int i=0;i<main_str.length()-w;i++){
                int j = i + w;
                for (int k=i;k<j;k++){
                    if (d[i][k].empty() || d[k+1][j].empty()) continue;
                    for (auto le : d[i][k]){
                        for (auto ri : d[k+1][j]){
                            string temp; temp+=le; temp+=ri;
                            if (db.find(temp)!=db.end()) d[i][j].insert(db[temp]->variable.begin(),db[temp]->variable.end());
                        }
                    }
                }
            }
        }
        if (d[0][main_str.length()-1].find(root)==d[0][main_str.length()-1].end()) cout << "no\n";
        else cout << "yes\n";
    }
    return 0;
}
