package view;
public enum UserType {
    ADMIN,
    TEACHER,
    STUDENT;
    public static UserType getType(int x) {
        switch (x){
            case 0: return ADMIN;
            case 1: return TEACHER;
            case 2: return STUDENT;
        }
        return null;
    }
}