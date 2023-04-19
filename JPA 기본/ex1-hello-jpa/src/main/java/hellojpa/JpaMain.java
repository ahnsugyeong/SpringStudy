package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            Team team = new Team();
//            team.setName("teamA");
//            em.persist(team);
//
//            Member member1 = new Member();
//            member1.setUsername("member1");
//            member1.setTeam(team);
//            em.persist(member1);
//
//
//            em.flush();
//            em.clear();

            // 프록시
//            Member refMember = em.getReference(Member.class, member1.getId());
//            System.out.println("refMember = " + refMember.getClass());  // Proxy

//            em.detach(refMember);
//            em.close();
//            em.clear();

//            refMember.getUsername();

//            Member findMember = em.find(Member.class, member1.getId());
//            System.out.println("findMember = " + findMember.getClass());    // Proxy
//
//            System.out.println("refMember == findMember: " + (refMember == findMember));    // ⭐️ JPA에서 이 식이 성립해야 함 !!!!

            // PersistenceUnitUtil
//            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));
//            Hibernate.initialize(refMember);    // 강제 초기화



            ////////////////////////////////////////////////////////
            // 즉시 로딩과 지연 로딩
//            Member m = em.find(Member.class, member1.getId());
//            System.out.println("m = " + m.getTeam().getClass());
//
//            m.getTeam().getName();


            // 영속성 전이(CASCADE)와 고아 객체
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        emf.close();
    }

    private static void printMember(Member member) {
        System.out.println("username = " + member.getUsername());
    }
    private static void printMemberAndTeam(Member member) {
        String username = member.getUsername();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }

}
