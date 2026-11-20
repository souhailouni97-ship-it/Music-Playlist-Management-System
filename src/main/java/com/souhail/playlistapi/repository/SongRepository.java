package com.souhail.playlistapi.repository;

import com.souhail.playlistapi.model.Genre;
import com.souhail.playlistapi.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    List<Song> findByGenre(Genre genre);
    List<Song> findByArtistIgnoreCase(String artist);
}