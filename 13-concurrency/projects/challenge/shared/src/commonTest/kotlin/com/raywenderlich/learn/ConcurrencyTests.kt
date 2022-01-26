/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.learn

import com.raywenderlich.learn.data.FeedAPI
import com.raywenderlich.learn.data.model.GravatarEntry
import com.raywenderlich.learn.data.model.GravatarProfile
import com.raywenderlich.learn.platform.runTest
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlin.test.*

class ConcurrencyTests {

  private val mockGravatar = GravatarProfile(
    entry = listOf(
      GravatarEntry(
        id = "1000",
        hash = "1000",
        preferredUsername = "Ray Wenderlich",
        thumbnailUrl = "https://avatars.githubusercontent.com/u/4722515?s=200&v=4"
      )
    )
  )

  @RelaxedMockK
  private lateinit var feedAPI: FeedAPI

  @BeforeTest
  fun setup() {
    MockKAnnotations.init(this)
  }

  @OptIn(ExperimentalCoroutinesApi::class)
  @Test
  fun `testFetchMyGravatar`() = runTest {

    coEvery { feedAPI.fetchMyGravatar("hash") } returns mockGravatar

    val result = feedAPI.fetchMyGravatar("hash")
    assertEquals(mockGravatar, result)
  }
}