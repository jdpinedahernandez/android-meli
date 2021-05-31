package com.juanpineda.meli

import android.app.Application
import android.content.Intent
import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.juanpineda.meli.ui.modules.home.activity.view.HomeActivity
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

class UiTest {

    @ExperimentalTime
    @get:Rule
    val activityTestRule = ActivityTestRule(HomeActivity::class.java, false, false)


    private lateinit var mockWebServer: MockWebServer
    private lateinit var resource: OkHttp3IdlingResource

    @ExperimentalTime
    @Before
    fun setUp() {
        val instrumentation = InstrumentationRegistry.getInstrumentation()
        val app = instrumentation.targetContext.applicationContext as Application
        val component = DaggerUiTestComponent.factory().create(app)

        mockWebServer = component.mockWebServer

        resource = OkHttp3IdlingResource.create("OkHttp", component.meliDB.okHttpClient)
        IdlingRegistry.getInstance().register(resource)

        val intent = Intent(instrumentation.targetContext, HomeActivity::class.java)

        activityTestRule.launchActivity(intent)
    }

    @Test
    fun clickACategoryShowAssociatesProductsAndNavigateToProductDetailValidateDetailTitle() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(categories)
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(predictiveCategory)
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(productsByCategory)
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(productDetailByCategory)
        )
        SystemClock.sleep(1000)
        onView(withId(R.id.search_button))
            .perform(click())
        onView(withId(R.id.search_src_text))
            .perform(typeText("b"))
        SystemClock.sleep(1000)
        onView(withId(R.id.recycler_view_searching)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        SystemClock.sleep(1000)
        onView(withId(R.id.recycler_view_products)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                click()
            )
        )
        SystemClock.sleep(2000)
        onView(withId(R.id.product_detail_summary))
            .check(matches(withText("B&o Play De Bang & Olufsen A2")))
    }

    @Test
    fun clickACategoryShowEmptyState() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(categories)
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(predictiveCategory)
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(emptyProductsByCategory)
        )
        SystemClock.sleep(1000)
        onView(withId(R.id.search_button))
            .perform(click())
        onView(withId(R.id.search_src_text))
            .perform(typeText("b"))
        SystemClock.sleep(1000)
        onView(withId(R.id.recycler_view_searching)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        SystemClock.sleep(2000)
        onView(withId(R.id.text_view_information_title))
            .check(matches(withText("No hay productos registrados.")))
    }

    @After
    fun tearDown() {
        mockWebServer.close()
        mockWebServer.shutdown()
        IdlingRegistry.getInstance().unregister(resource)
    }
}