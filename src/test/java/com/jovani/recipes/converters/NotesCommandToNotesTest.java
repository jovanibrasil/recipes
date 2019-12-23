package com.jovani.recipes.converters;

import com.jovani.recipes.commands.NotesCommand;
import com.jovani.recipes.domain.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    public static final Long NOTE_ID = 1L;
    public static final String NOTES = "My notes";

    private NotesCommandToNotes notesCommandToNotes;

    @BeforeEach
    void setUp() {
        this.notesCommandToNotes = new NotesCommandToNotes();
    }

    @Test
    void convert() {

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTE_ID);
        notesCommand.setRecipeNotes(NOTES);

        Notes notes = notesCommandToNotes
                .convert(notesCommand);

        assertEquals(NOTE_ID, notes.getId());
        assertEquals(NOTES, notes.getRecipeNotes());

    }
}