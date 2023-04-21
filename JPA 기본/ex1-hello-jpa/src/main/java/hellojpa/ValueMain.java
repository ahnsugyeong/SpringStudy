package hellojpa;

public class ValueMain {
    public static void main(String[] args) {
        int a = 10;
        int b = 10;

        Address address1 = new Address("city", "street", "10000");
        Address address2 = new Address("city", "street", "10000");
        System.out.println("a == b: " + (a == b));

        // ⭐️ Embedded type -> equals 재정의 하기 !
        System.out.println("address1 == address2: " + (address1 == address2));  // false
        System.out.println("address1 equals address2: " + (address1.equals(address2))); // equals 재정의 -> true

    }
}
