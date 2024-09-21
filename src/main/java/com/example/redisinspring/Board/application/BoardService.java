package com.example.redisinspring.Board.application;


import com.example.redisinspring.Board.domain.entity.Board;
import com.example.redisinspring.Board.domain.repository.BoardRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // Cacheable :
    // 이 어노테이션은 메서드 호출 결과를 캐시에 저장하고,
    // 동일한 파라미터로 메서드를 호출할 때 캐시된 데이터를 반환하게 합니다
    // 즉, 같은 요청이 반복되더라도 캐시에 저장된 데이터를 빠르게 반환하여 성능을 개선합니다.
    @Cacheable(cacheNames = "getBoards", key = "'boards:page:' + #page + ':size:' + #size", cacheManager = "boardCacheManager")
    public List<Board> getBoards(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Board> pageOfBoards = boardRepository.findAllByOrderByCreatedAtDesc(pageable);
        return pageOfBoards.getContent();
    }
    // cacheNames : 캐시 이름을 설정
    // cacheManager : 사용할 캐시 매니저의 빈 이름

    // 반환된거 전체가 다 value로 들어감
    // {
    //    "id": 505915,
    //    "title": "Title0505915",
    //    "content": "Content0505915",
    //    "createdAt": "2024-09-21T15:14:18"
    //  },
    //  {
    //    "id": 519731,
    //    "title": "Title0519731",
    //    "content": "Content0519731",
    //    "createdAt": "2024-09-21T15:02:49"
    //  },
}
