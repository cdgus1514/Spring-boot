package com.example.service.freeboard;

import com.example.model.Freeboard;
import com.example.pageMaker.PageMaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PageMakerService {

    public PageMaker generatePageMaker(int pageNum, int contentNum, JpaRepository<Freeboard, Long> repository){
        PageMaker pageMaker = new PageMaker();

        int totalCount = (int) repository.count();
        pageMaker.setTotalcount(totalCount);    // 전체 게시글 수 지정
        pageMaker.setPagenum(pageNum-1);        // 현재 페이지를 페이지 객체로 지정 (-1해야 사용할 수 있음)
        pageMaker.setContentnum(contentNum);    // 한 페이지에 몇개씩 게시글을 보여줄지 지정
        pageMaker.setCurrentblock(pageNum);     // 현재 페이지 블록이 몇 번인지 현재 페이지를 통해 지정
        pageMaker.setLastblock(pageMaker.getTotalcount());      // 마지막 블록 번호를 전체 게시글 수를 통해 지정
        pageMaker.prevnext(pageNum);            // 현재 페이지 번호로 화살표를 나타낼지 정함
        pageMaker.setStartPage(pageMaker.getCurrentblock());    // 시작 페이지를 페이지 블록 번호로 정함
        pageMaker.setEndPage(pageMaker.getLastblock(), pageMaker.getCurrentblock());    // 마지막 페이지를 마지막 페이지 블록과 현재 페이지 블록 번호로 정함);

        return pageMaker;
    }
}
