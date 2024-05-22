class NotesApp {
    private val archives = mutableListOf<Archive>()

    fun run() {
        while (true) {
            showMainMenu()
        }
    }

    private fun showMainMenu() {
        println("Список архивов:")
        println("0. Создать архив")
        archives.forEachIndexed { index, archive -> println("${index + 1}. ${archive.name}") }
        println("${archives.size + 1}. Выход")

        when (val choice = readInt(archives.size + 1)) {
            0 -> createArchive()
            in 1..archives.size -> showArchiveMenu(archives[choice - 1])
            archives.size + 1 -> exit()
        }
    }

    private fun createArchive() {
        println("Введите имя нового архива:")
        val name = readNonEmptyLine()
        archives.add(Archive(name))
    }

    private fun showArchiveMenu(archive: Archive) {
        while (true) {
            println("Архив: ${archive.name}")
            println("0. Создать заметку")
            archive.notes.forEachIndexed { index, note -> println("${index + 1}. ${note.title}") }
            println("${archive.notes.size + 1}. Назад")

            when (val choice = readInt(archive.notes.size + 1)) {
                0 -> createNoteInArchive(archive)
                in 1..archive.notes.size -> showNoteContent(archive.notes[choice - 1])
                archive.notes.size + 1 -> return
            }
        }
    }

    private fun createNoteInArchive(archive: Archive) {
        println("Введите заголовок заметки:")
        val title = readNonEmptyLine()
        println("Введите текст заметки:")
        val content = readNonEmptyLine()
        archive.notes.add(Note(title, content))
    }

    private fun showNoteContent(note: Note) {
        println("Заголовок: ${note.title}")
        println("Содержание: ${note.content}")
        println("Нажмите Enter для возврата")
        readlnOrNull()
    }

    private fun exit() {
        println("Выход из программы.")
        System.exit(0)
    }

    private fun readInt(max: Int): Int {
        while (true) {
            val input = readlnOrNull()
            try {
                val choice = input?.toInt()
                if (choice != null && choice in 0..max) {
                    return choice
                }
            } catch (e: NumberFormatException) {
                // ignore and prompt again
            }
            println("Введите цифру от 0 до $max:")
        }
    }

    private fun readNonEmptyLine(): String {
        while (true) {
            val input = readlnOrNull()
            if (!input.isNullOrBlank()) {
                return input
            }
            println("Введите непустую строку:")
        }
    }
}
