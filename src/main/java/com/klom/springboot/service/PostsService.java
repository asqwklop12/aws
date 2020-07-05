package com.klom.springboot.service;

import com.klom.springboot.domain.posts.PostRepository;
import com.klom.springboot.domain.posts.Posts;
import com.klom.springboot.web.dto.PostResponseDto;
import com.klom.springboot.web.dto.PostSaveRequestDto;
import com.klom.springboot.web.dto.PostUpdateRequestDto;
import com.klom.springboot.web.dto.PostsListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostRepository postRepository;

    @Transactional
    public Long save(PostSaveRequestDto requestDto) {
        return postRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostUpdateRequestDto requestDto) {
        Posts posts = postRepository
                    .findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));

        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));
        postRepository.delete(posts);
    }

    public PostResponseDto findById(Long id) {
        Posts entity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id = " + id));
        return new PostResponseDto(entity);
    }

    public List<PostsListResponseDto> findAllDesc() {
        return postRepository.findAllDesc().stream()
                            .map(PostsListResponseDto::new)
                            .collect(toList());
    }
}
