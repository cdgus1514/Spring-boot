package com.example.service.freeboard;

import com.example.model.Freeboard;
import com.example.pageMaker.PageMaker;
import com.example.repository.FreeboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class FreeboardListService {

    @Autowired
    private FreeboardRepository freeboardRepository;

    @Autowired
    private HttpSession session;

    @Autowired
    private PageMakerService pageMakerService;

    public String freeboardList(int pageNum) {

        PageMaker pageMaker = pageMakerService.generatePageMaker(pageNum, 10, freeboardRepository);

        // 페이징 구현
        PageRequest pageRequest = PageRequest.of(pageNum-1, 10, Sort.Direction.DESC, "freeid");
        /*
        >> 페이지당 10개씩 게시글 출력
        ** properties 이름 넣을경우 데이터베이스에서 _ 사용을 권장하지만 스프링에서 _ 기준으로 짤라서 인식하는 경우 에러 발생
         */
        Page<Freeboard> freeboardPage = freeboardRepository.findAll(pageRequest);

        if (freeboardPage.getSize() == 0) {
            // size가 0이고 null은 아님
            session.setAttribute("boardList", new ArrayList<Freeboard>());
            return "freeboard";
        }

        List<Freeboard> freeboardList = freeboardPage.getContent();
        session.setAttribute("boardList", freeboardList);
        session.setAttribute("pageMaker", pageMaker);

        return "freeboard";
    }
}
