package com.example.aplicacionlogin

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.FragmentActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageButton

class ChatFloatingManager(private val application: Application) : Application.ActivityLifecycleCallbacks {

    private val TAG = "ChatFloatingManager"
    private val viewTag = "__FLOATING_CHAT_BUTTON__"

    fun register() {
        application.registerActivityLifecycleCallbacks(this)
    }

    fun unregister() {
        application.unregisterActivityLifecycleCallbacks(this)
    }

    private fun dpToPx(context: Context, dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        addFloatingButtonToActivity(activity)
    }

    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        removeFloatingButtonFromActivity(activity)
    }

    private fun addFloatingButtonToActivity(activity: Activity) {
        // No añadimos el botón dentro de la propia AsistenteActivity para evitar bucles
        if (activity is AsistenteActivity) return

        val decor = activity.window?.decorView as? ViewGroup ?: return

        // Evitar duplicados
        if (decor.findViewWithTag<View>(viewTag) != null) return

        val inflater = LayoutInflater.from(activity)
        val fabView = inflater.inflate(R.layout.floating_chat_button, decor, false)
        fabView.tag = viewTag

        // Encontrar el ImageButton y configurar comportamiento
        val fabButton = fabView.findViewById<ImageButton>(R.id.fabChat)
        fabButton.setOnClickListener {
            // Abrir Asistente como BottomSheet si la Activity soporta FragmentManager
            val act = activity
            if (act is FragmentActivity) {
                // Ocultar FAB inmediatamente
                fabView.visibility = View.GONE
                val fragment = AsistenteBottomSheetFragment()
                fragment.show(act.supportFragmentManager, "asistente_bottom_sheet")
            } else {
                // Fallback: abrir AsistenteActivity
                val intent = Intent(activity, AsistenteActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                activity.startActivity(intent)
            }
        }

        // Layout params para posicionarlo bottom-end (derecha)
        val size = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT,
            Gravity.END or Gravity.BOTTOM
        )
        val margin = dpToPx(activity, 16)
        size.setMargins(margin, margin, margin, margin + dpToPx(activity, 56))

        // Añadir al decor y traer al frente
        decor.addView(fabView, size)
        fabView.bringToFront()
    }

    private fun removeFloatingButtonFromActivity(activity: Activity) {
        val decor = activity.window?.decorView as? ViewGroup ?: return
        val existing = decor.findViewWithTag<View>(viewTag)
        if (existing != null) {
            decor.removeView(existing)
        }
    }
}