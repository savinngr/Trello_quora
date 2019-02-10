package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.AnswerDao;
import com.upgrad.quora.service.entity.AnswerEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AnswerNotFoundException;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerDao answerDao;

    public AnswerEntity createAnswer(AnswerEntity answerEntity) {
        return answerDao.createAnswer(answerEntity);
    }

    public AnswerEntity getAnswerFromId(String uuid) throws AnswerNotFoundException {
        AnswerEntity answerEntity = answerDao.getAnswerById(uuid);
        if (answerEntity == null)
        {
            throw new AnswerNotFoundException("ANS-001","Entered answer uuid does not exist");
        }
        return answerEntity;
    }

    public AnswerEntity checkAnswerBelongToUser(UserEntity userEntity, AnswerEntity answerEntity) throws AuthorizationFailedException {
        AnswerEntity checkedAnswer = answerDao.checkAnswerBelongToUser(userEntity,answerEntity);
        if(checkedAnswer==null)
        {
            throw new AuthorizationFailedException("'ATHR-003","Only the answer owner can edit or delete the answer");
        }
        return checkedAnswer;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AnswerEntity updateAnswer(AnswerEntity answerEntity)
    {
        AnswerEntity updateAnswer = answerDao.updateAnswer(answerEntity);
        return updateAnswer;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AnswerEntity deleteAnswer(AnswerEntity answerEntity)
    {
        AnswerEntity deletedAnswer = answerDao.deleteAnswer(answerEntity);
        return deletedAnswer;
    }

    public List<AnswerEntity> getAllAnswers(String questionId)
    {
        return answerDao.getAllAnswers(questionId);
    }
}