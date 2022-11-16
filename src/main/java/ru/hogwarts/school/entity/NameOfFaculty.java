package ru.hogwarts.school.entity;

public enum NameOfFaculty {
    Gryffindor("Gryffindor"),
    Hufflepuff("Hufflepuff"),
    Ravenclaw("Ravenclaw"),
    Slytherin("Slytherin");
    private final String type;
    NameOfFaculty(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}