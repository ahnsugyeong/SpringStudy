package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
//            Member findMember = em.find(Member.class, 1L);
            // JPQL
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(5)
//                    .setMaxResults(8)
//                    .getResultList();
//            for (Member member : result) {
//                System.out.println("member.name = " + member.getName());
//            }

//            findMember.setName("helloJPA");
//            em.remove(findMember);
//            tx.commit();

//            // 비영속
//            Member member = new Member();
//            member.setId(101L);
//            member.setName("helloJPA");
//
//            // 영속
//            em.persist(member);
//
//            // 1차 캐시에서 조회
//            Member findMember = em.find(Member.class, 101L);
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.name = " + findMember.getName());

//            // DB에서 조회 후 1차 캐시에 저장
//            Member findMember1 = em.find(Member.class, 101L);
//            // 1차 캐시에서 조회
//            Member findMember2 = em.find(Member.class, 101L);
//
//            // 영속 엔티티의 동일성 보장
//            System.out.println(findMember1 == findMember2);

//            Member member1 = new Member(150L, "A");
//            Member member2 = new Member(160L, "B");
//
//            em.persist(member1);
//            em.persist(member2);

//            Member member = em.find(Member.class, 150L);
//            // 영속  컨텍스트 -> 변경 감지(dirty checking). persist 할 필요 없음!
//            member.setName("ZZZ");

//            Member member = new Member(200L, "member200");
//            em.persist(member);
//            em.flush();
//            System.out.println("==========");

//            Member member = em.find(Member.class, 150L);
//            member.setName("AAA");
//
////            em.detach(member);
//            em.clear();
//            Member member2 = em.find(Member.class, 150L);

//            Member member = new Member();
//            member.setId(1L);
//            member.setUsername("A");
//            member.setRoleType(RoleType.USER);
//
//            em.persist(member);

            Member member = new Member();
            member.setUsername("userA");
            em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
