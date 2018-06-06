package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CommentServiceImplJPA implements CommentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addComment(Comment comment){
        entityManager.persist(comment);
    }

    @Override
    public List<Comment> getListOfCommentsByGame(String game){
        return entityManager.createNamedQuery("Comment.getCommentsByGame").setParameter("game", game).getResultList();
    }

    @Override
    public Comment getcomment(Integer id){
        return entityManager.find(Comment.class, 1);
    }
}
