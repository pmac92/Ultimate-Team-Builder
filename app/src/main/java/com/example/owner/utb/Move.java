package com.example.owner.utb;

public class Move
{
  int idNum;
  String internalName;
  String displayName;
  int baseDamage;
  TypeEnum type;
  MoveCategory category;
  int accuracy;
  int effectChance;
  int priority;
  String description;
  
  public Move(int idNum, String internalName,String displayName,int baseDamage,TypeEnum type,MoveCategory category,
      int accuracy, int effectChance, int priority, String description)
  {
    this.idNum = idNum;
    this.internalName = internalName;
    this.displayName = displayName;
    this.baseDamage = baseDamage;
    this.type = type;
    this.category = category;
    this.accuracy = accuracy;
    this.effectChance = effectChance;
    this.priority = priority;
    this.description = description;
  }
  
  @Override
  public String toString()
  {
    return displayName;
  }
}
