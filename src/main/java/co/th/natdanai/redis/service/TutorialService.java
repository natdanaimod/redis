package co.th.natdanai.redis.service;

import co.th.natdanai.redis.repository.RedisRepository;
import co.th.natdanai.redis.repository.TutorialRepository;
import co.th.natdanai.redis.entity.Tutorial;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static co.th.natdanai.redis.constant.RedisKeyConstant.ALL_TUTORIALS;

@Service
public class TutorialService {

    private TutorialRepository tutorialRepository;

    private RedisRepository redisRepository;

    private Gson gson;

    @Autowired
    public TutorialService(TutorialRepository tutorialRepository, RedisRepository redisRepository, Gson gson) {
        this.tutorialRepository = tutorialRepository;
        this.redisRepository = redisRepository;
        this.gson = gson;
    }

    public List<Tutorial> getAll() {

        boolean isExist = redisRepository.isExistKey(ALL_TUTORIALS);

        if (isExist) {
            return gson.fromJson(redisRepository.findByKey(ALL_TUTORIALS), new TypeToken<ArrayList<Tutorial>>() {
            }.getType());
        }

        List<Tutorial> tutorials = tutorialRepository.findAll();
        if (!ObjectUtils.isEmpty(tutorials)) {
            redisSave(ALL_TUTORIALS, gson.toJson(tutorials), 3000, TimeUnit.SECONDS);
        }
        return tutorials;
    }

    public List<Tutorial> getTutorialByTitleContaining(String title) {

        boolean isExist = redisRepository.isExistKey(title);

        if (isExist) {
            return gson.fromJson(redisRepository.findByKey(title), new TypeToken<ArrayList<Tutorial>>() {
            }.getType());
        }

        List<Tutorial> tutorials = tutorialRepository.findByTitleContaining(title);
        if (!ObjectUtils.isEmpty(tutorials)) {
            redisSave(title, gson.toJson(tutorials), 3000, TimeUnit.SECONDS);
        }
        return tutorials;

    }

    public Tutorial getTutorialById(String id) {

        boolean isExist = redisRepository.isExistKey(id);

        if (isExist) {
            return gson.fromJson(redisRepository.findByKey(id), Tutorial.class);
        }

        Optional<Tutorial> tutorial = tutorialRepository.findById(Long.valueOf(id));
        if (tutorial.isPresent()) {
            redisSave(id, gson.toJson(tutorial.get()), 1600, TimeUnit.SECONDS);
        }
        return tutorial.get();
    }

    private void redisSave(String key, String value, Integer ttl, TimeUnit timeUnit) {
        redisRepository.save(key, value, ttl, timeUnit);
    }

    public Tutorial save(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }
}
