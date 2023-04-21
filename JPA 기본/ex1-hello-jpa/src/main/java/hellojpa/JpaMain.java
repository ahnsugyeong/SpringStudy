package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 값 타입 -> 불변 객체! (setter private으로 설정)
            Address address = new Address("homeCity", "street", "10000");

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(address);
//            em.persist(member);
//
//            Address newAddress = new Address("newCity", "street", "10000");
//            member.setHomeAddress(newAddress);


            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");

//            member.getAddressHistory().add(new Address("old1", "street", "10000"));
//            member.getAddressHistory().add(new Address("old2", "street", "10000"));

            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("==========");
            Member findMember = em.find(Member.class, member.getId());


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

}
