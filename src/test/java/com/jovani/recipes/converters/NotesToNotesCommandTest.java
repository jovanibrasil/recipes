package com.jovani.recipes.converters;

import com.jovani.recipes.commands.NotesCommand;
import com.jovani.recipes.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotesToNotesCommandTest {

    public static final String NOTE_ID = "1L";
    public static final String NOTES = "My notes";

    private NotesToNotesCommand notesToNotesCommand;

    @BeforeEach
    void setUp() {
        this.notesToNotesCommand = new NotesToNotesCommand();
    }

    @Test
    void convert() {

        Notes notes = new Notes();
        notes.setId(NOTE_ID);
        notes.setRecipeNotes(NOTES);

        NotesCommand notesCommand = notesToNotesCommand
                .convert(notes);

        assertEquals(NOTE_ID, notesCommand.getId());
        assertEquals(NOTES, notesCommand.getRecipeNotes());

    }
}