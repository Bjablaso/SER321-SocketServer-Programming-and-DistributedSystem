package example.grpcclient;

public enum FlowerAge {
    SEEDING(0),
    SEEDGERMINATION(5),
    LEAVESSPROUTS(15),
    BLOOM(20);

    private final int age;

    FlowerAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

}
