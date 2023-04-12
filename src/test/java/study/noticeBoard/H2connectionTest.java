package study.noticeBoard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import study.noticeBoard.entity.Posts;
import study.noticeBoard.repository.PostsRepository;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class H2connectionTest {

    @Autowired
    PostsRepository boardRepository;

    @Test
    @Rollback(false)
    void saveBoard() {

        List<Posts> List1 = new ArrayList<>();

        List<Posts> List2 = null;

        List<Posts> List3;

        if (List1.isEmpty()) {
            System.out.println(" null");

        }
        if (List2== null) {
            System.out.println(" null");

        }


    }
}
