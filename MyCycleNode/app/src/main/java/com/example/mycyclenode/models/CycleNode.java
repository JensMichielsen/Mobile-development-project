package com.example.mycyclenode.models;

import java.io.Serializable;

public class CycleNode implements Serializable {
     public String Id;
     public int CycleNode;
     public String City;
     public String Province;
     public String Coordinates;

     public CycleNode() {};

     public CycleNode(String id, int cycleNode, String city, String province, String coordinates) {
          Id = id;
          CycleNode = cycleNode;
          City = city;
          Province = province;
          Coordinates = coordinates;
     }
}
