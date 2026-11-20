package com.souhail.playlistapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "playlist_songs",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    @OrderColumn(name = "position")
    private List<Song> songs = new ArrayList<>();

    public Playlist(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public boolean removeSong(Song song) {
        return songs.remove(song);
    }

    public void moveSong(int fromIndex, int toIndex) {
        if (!isValidIndex(fromIndex) || !isValidIndex(toIndex)) {
            throw new IllegalArgumentException("Invalid song position");
        }
        Song song = songs.remove(fromIndex);
        songs.add(toIndex, song);
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < songs.size();
    }
}