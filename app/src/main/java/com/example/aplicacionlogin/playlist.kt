package com.example.aplicacionlogin

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class playlist : AppCompatActivity() {

    private var mediaPlayer1: MediaPlayer? = null
    private var mediaPlayer2: MediaPlayer? = null
    private var mediaPlayer3: MediaPlayer? = null
    private var mediaPlayer4: MediaPlayer? = null
    private var mediaPlayer5: MediaPlayer? = null
    private var mediaPlayer6: MediaPlayer? = null
    private var mediaPlayer7: MediaPlayer? = null
    private var mediaPlayer8: MediaPlayer? = null
    private var mediaPlayer9: MediaPlayer? = null
    private var mediaPlayer10: MediaPlayer? = null // 🎵 NUEVO para ayer.mp3

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist)

        val playButton1 = findViewById<Button>(R.id.playButton1)
        val playButton2 = findViewById<Button>(R.id.playButton2)
        val playButton3 = findViewById<Button>(R.id.playButton3)
        val playButton4 = findViewById<Button>(R.id.playButton4)
        val playButton5 = findViewById<Button>(R.id.playButton5)
        val playButton6 = findViewById<Button>(R.id.playButton6)
        val playButton7 = findViewById<Button>(R.id.playButton7)
        val playButton8 = findViewById<Button>(R.id.playButton8)
        val playButton9 = findViewById<Button>(R.id.playButton9)
        val playButton10 = findViewById<Button>(R.id.playButton10) // 🎵 NUEVO para ayer.mp3

        // 🎵 Botón 1: querida.mp3
        playButton1.setOnClickListener {
            if (mediaPlayer1 == null) {
                mediaPlayer1 = MediaPlayer.create(this, R.raw.querida)
                mediaPlayer1?.start()
                playButton1.text = "Pausar"
            } else {
                if (mediaPlayer1!!.isPlaying) {
                    mediaPlayer1?.pause()
                    playButton1.text = "Reproducir"
                } else {
                    mediaPlayer1?.start()
                    playButton1.text = "Pausar"
                }
            }

            mediaPlayer1?.setOnCompletionListener {
                playButton1.text = "Reproducir"
                mediaPlayer1?.release()
                mediaPlayer1 = null
            }
        }

        // 🎵 Botón 2: igual.mp3
        playButton2.setOnClickListener {
            if (mediaPlayer2 == null) {
                mediaPlayer2 = MediaPlayer.create(this, R.raw.igual)
                mediaPlayer2?.start()
                playButton2.text = "Pausar"
            } else {
                if (mediaPlayer2!!.isPlaying) {
                    mediaPlayer2?.pause()
                    playButton2.text = "Reproducir"
                } else {
                    mediaPlayer2?.start()
                    playButton2.text = "Pausar"
                }
            }

            mediaPlayer2?.setOnCompletionListener {
                playButton2.text = "Reproducir"
                mediaPlayer2?.release()
                mediaPlayer2 = null
            }
        }

        // 🎵 Botón 3: color.mp3
        playButton3.setOnClickListener {
            if (mediaPlayer3 == null) {
                mediaPlayer3 = MediaPlayer.create(this, R.raw.color)
                mediaPlayer3?.start()
                playButton3.text = "Pausar"
            } else {
                if (mediaPlayer3!!.isPlaying) {
                    mediaPlayer3?.pause()
                    playButton3.text = "Reproducir"
                } else {
                    mediaPlayer3?.start()
                    playButton3.text = "Pausar"
                }
            }

            mediaPlayer3?.setOnCompletionListener {
                playButton3.text = "Reproducir"
                mediaPlayer3?.release()
                mediaPlayer3 = null
            }
        }

        // 🎵 Botón 4: valor.mp3
        playButton4.setOnClickListener {
            if (mediaPlayer4 == null) {
                mediaPlayer4 = MediaPlayer.create(this, R.raw.valor)
                mediaPlayer4?.start()
                playButton4.text = "Pausar"
            } else {
                if (mediaPlayer4!!.isPlaying) {
                    mediaPlayer4?.pause()
                    playButton4.text = "Reproducir"
                } else {
                    mediaPlayer4?.start()
                    playButton4.text = "Pausar"
                }
            }

            mediaPlayer4?.setOnCompletionListener {
                playButton4.text = "Reproducir"
                mediaPlayer4?.release()
                mediaPlayer4 = null
            }
        }

        // 🎵 Botón 5: vivir.mp3
        playButton5.setOnClickListener {
            if (mediaPlayer5 == null) {
                mediaPlayer5 = MediaPlayer.create(this, R.raw.vivir)
                mediaPlayer5?.start()
                playButton5.text = "Pausar"
            } else {
                if (mediaPlayer5!!.isPlaying) {
                    mediaPlayer5?.pause()
                    playButton5.text = "Reproducir"
                } else {
                    mediaPlayer5?.start()
                    playButton5.text = "Pausar"
                }
            }

            mediaPlayer5?.setOnCompletionListener {
                playButton5.text = "Reproducir"
                mediaPlayer5?.release()
                mediaPlayer5 = null
            }
        }

        // 🎵 Botón 6: unica.mp3
        playButton6.setOnClickListener {
            if (mediaPlayer6 == null) {
                mediaPlayer6 = MediaPlayer.create(this, R.raw.unica)
                mediaPlayer6?.start()
                playButton6.text = "Pausar"
            } else {
                if (mediaPlayer6!!.isPlaying) {
                    mediaPlayer6?.pause()
                    playButton6.text = "Reproducir"
                } else {
                    mediaPlayer6?.start()
                    playButton6.text = "Pausar"
                }
            }

            mediaPlayer6?.setOnCompletionListener {
                playButton6.text = "Reproducir"
                mediaPlayer6?.release()
                mediaPlayer6 = null
            }
        }

        // 🎵 Botón 7: celebra.mp3
        playButton7.setOnClickListener {
            if (mediaPlayer7 == null) {
                mediaPlayer7 = MediaPlayer.create(this, R.raw.celebra)
                mediaPlayer7?.start()
                playButton7.text = "Pausar"
            } else {
                if (mediaPlayer7!!.isPlaying) {
                    mediaPlayer7?.pause()
                    playButton7.text = "Reproducir"
                } else {
                    mediaPlayer7?.start()
                    playButton7.text = "Pausar"
                }
            }

            mediaPlayer7?.setOnCompletionListener {
                playButton7.text = "Reproducir"
                mediaPlayer7?.release()
                mediaPlayer7 = null
            }
        }

        // 🎵 Botón 8: carnaval.mp3
        playButton8.setOnClickListener {
            if (mediaPlayer8 == null) {
                mediaPlayer8 = MediaPlayer.create(this, R.raw.carnaval)
                mediaPlayer8?.start()
                playButton8.text = "Pausar"
            } else {
                if (mediaPlayer8!!.isPlaying) {
                    mediaPlayer8?.pause()
                    playButton8.text = "Reproducir"
                } else {
                    mediaPlayer8?.start()
                    playButton8.text = "Pausar"
                }
            }

            mediaPlayer8?.setOnCompletionListener {
                playButton8.text = "Reproducir"
                mediaPlayer8?.release()
                mediaPlayer8 = null
            }
        }

        // 🎵 Botón 9: creo.mp3
        playButton9.setOnClickListener {
            if (mediaPlayer9 == null) {
                mediaPlayer9 = MediaPlayer.create(this, R.raw.creo)
                mediaPlayer9?.start()
                playButton9.text = "Pausar"
            } else {
                if (mediaPlayer9!!.isPlaying) {
                    mediaPlayer9?.pause()
                    playButton9.text = "Reproducir"
                } else {
                    mediaPlayer9?.start()
                    playButton9.text = "Pausar"
                }
            }

            mediaPlayer9?.setOnCompletionListener {
                playButton9.text = "Reproducir"
                mediaPlayer9?.release()
                mediaPlayer9 = null
            }
        }

        // 🎵 Botón 10: ayer.mp3
        playButton10.setOnClickListener {
            if (mediaPlayer10 == null) {
                mediaPlayer10 = MediaPlayer.create(this, R.raw.ayer)
                mediaPlayer10?.start()
                playButton10.text = "Pausar"
            } else {
                if (mediaPlayer10!!.isPlaying) {
                    mediaPlayer10?.pause()
                    playButton10.text = "Reproducir"
                } else {
                    mediaPlayer10?.start()
                    playButton10.text = "Pausar"
                }
            }

            mediaPlayer10?.setOnCompletionListener {
                playButton10.text = "Reproducir"
                mediaPlayer10?.release()
                mediaPlayer10 = null
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer1?.release()
        mediaPlayer2?.release()
        mediaPlayer3?.release()
        mediaPlayer4?.release()
        mediaPlayer5?.release()
        mediaPlayer6?.release()
        mediaPlayer7?.release()
        mediaPlayer8?.release()
        mediaPlayer9?.release()
        mediaPlayer10?.release() // 🎵 NUEVO
        mediaPlayer1 = null
        mediaPlayer2 = null
        mediaPlayer3 = null
        mediaPlayer4 = null
        mediaPlayer5 = null
        mediaPlayer6 = null
        mediaPlayer7 = null
        mediaPlayer8 = null
        mediaPlayer9 = null
        mediaPlayer10 = null // 🎵 NUEVO
    }
}
