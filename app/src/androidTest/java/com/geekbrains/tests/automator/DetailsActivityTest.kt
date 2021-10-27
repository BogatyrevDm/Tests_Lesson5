package com.geekbrains.tests.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class DetailsActivityTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setup() {
        uiDevice.pressHome()

        val intent = context.packageManager.getLaunchIntentForPackage(packageName)

        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)

        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), DetailsActivityTest.TIMEOUT)

        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )

        toDetails.click()

        uiDevice.wait(
            Until.findObject(By.res(packageName, "totalCountTextView")),
            DetailsActivityTest.TIMEOUT
        )
    }

    @Test
    fun test_TotalCountTextView() {

        val changedText =
            uiDevice.findObject(By.res(packageName, "totalCountTextView"))


        Assert.assertEquals(changedText.text.toString(), "Number of results: 0")
    }

    @Test
    fun test_DecrementButton() {

        val decrementButton =
            uiDevice.findObject(By.res(packageName, "decrementButton"))

        decrementButton.click()

        val changedText =
            uiDevice.findObject(By.res(packageName, "totalCountTextView"))


        Assert.assertEquals(changedText.text.toString(), "Number of results: -1")
    }

    @Test
    fun test_IncrementButton() {

        val incrementButton =
            uiDevice.findObject(By.res(packageName, "incrementButton"))

        incrementButton.click()

        val changedText =
            uiDevice.findObject(By.res(packageName, "totalCountTextView"))


        Assert.assertEquals(changedText.text.toString(), "Number of results: 1")
    }
    companion object {
        private const val TIMEOUT = 5000L
    }
}