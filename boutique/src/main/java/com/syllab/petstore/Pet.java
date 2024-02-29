package com.syllab.petstore;

public class Pet {
  public Pet() {
    this(-1, "?", Status.available);
  }

  public Pet(int id, String name, Status status) {
    this.id = id;
    this.name = name;
    this.photoUrls = new String[]{};
    this.status = status;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String[] getPhotoUrls() {
    return this.photoUrls;
  }

  public Status getStatus() {
    return this.status;
  }

  @Override
  public boolean equals(Object autre) {
    if (autre instanceof Pet) {
      var pet = (Pet) autre;
      return this.id == pet.id
          && this.name.equals(pet.name)
          && this.status == pet.status;
    } else {
      return false;
    }
  }

  private int id;
  private String name;
  private String[] photoUrls;
  private Status status;

  public enum Status {
    available, pending, sold
  }
}
