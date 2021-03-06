/**
 * Copyright (c) 2016 - 2017 Syncleus, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aparapi.codegen.test;

import com.aparapi.Kernel;

public class ObjectArrayMemberCall extends Kernel {

    final int size = 64;

    ;
    int out[] = new int[2];

    int something;

    DummyOOA dummy[] = null;

    public ObjectArrayMemberCall() {
        something = -1;
        dummy = new DummyOOA[size];

        dummy[0] = new DummyOOA();
    }

    public int getSomething() {
        return something;
    }

    public int bar(int x) {
        return -x;
    }

    public void run() {
        int myId = getGlobalId();
        dummy[myId].mem = dummy[myId].addEmUp(dummy[myId].mem, 2);
        int tmp = dummy[myId].addToMem(2);
        int tmp2 = dummy[myId].addEmUpPlusOne(2, tmp);
    }

    final static class DummyOOA {
        int mem;

        public DummyOOA() {
            mem = -3;
        }

        public int getMem() {
            return mem;
        }

        public void setMem(int x) {
            mem = x;
        }

        public int addEmUp(int x, int y) {
            return x + y;
        }

        public int addToMem(int x) {
            return x + mem;
        }

        public int addEmUpPlusOne(int x, int y) {
            return addEmUp(x, y) + 1 + getMem();
        }
    }
}

/**{OpenCL{
 typedef struct com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA_s{
 int  mem;

 } com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA;

 typedef struct This_s{
 __global com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA *dummy;
 int passid;
 }This;
 int get_pass_id(This *this){
 return this->passid;
 }

 int com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA__getMem( __global com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA *this){
 return(this->mem);
 }
 int com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA__addEmUp( __global com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA *this, int x, int y){
 return((x + y));
 }
 int com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA__addEmUpPlusOne( __global com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA *this, int x, int y){
 return(((com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA__addEmUp(this, x, y) + 1) + com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA__getMem(this)));
 }
 int com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA__addToMem( __global com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA *this, int x){
 return((x + this->mem));
 }
 __kernel void run(
 __global com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA *dummy,
 int passid
 ){
 This thisStruct;
 This* this=&thisStruct;
 this->dummy = dummy;
 this->passid = passid;
 {
 int myId = get_global_id(0);
 this->dummy[myId].mem=com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA__addEmUp( &(this->dummy[myId]), this->dummy[myId].mem, 2);
 int tmp = com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA__addToMem( &(this->dummy[myId]), 2);
 int tmp2 = com_amd_aparapi_test_ObjectArrayMemberCall$DummyOOA__addEmUpPlusOne( &(this->dummy[myId]), 2, tmp);
 return;
 }
 }
 }OpenCL}**/
