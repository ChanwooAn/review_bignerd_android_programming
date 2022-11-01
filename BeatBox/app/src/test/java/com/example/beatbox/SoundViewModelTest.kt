package com.example.beatbox

import org.hamcrest.MatcherAssert
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.internal.invocation.MatcherApplicationStrategy


internal class SoundViewModelTest {
    private lateinit var beatBox:BeatBox
    private lateinit var sound:Sound
    private lateinit var subject:SoundViewModel

    @Before
    fun setUp() {
        beatBox=mock(BeatBox::class.java)
        sound=Sound("assetPath")
        subject= SoundViewModel(beatBox)
        subject.sound=sound
    }
    //before 키워드는 각 테스트가 실행되기 전 딱 한번만 실행됨.
    //테스트할 SoundViewModel의 인스턴스와 Sound의 인스턴스를 생성해야 한다.

    @Test
    fun exposesSoundNameAsTitle(){
        MatcherAssert.assertThat(subject.title,`is`(sound.name))
        //subject의 title이 sound.name과 값이 같아야 test가 성공한다.
        //즉 Sound의 viewModel에 sound가 잘 지정되는지 보는 test라고 보면 된다.
    }
    @Test
    fun callBeatBoxPlayOnButtonClicked(){
        subject.onButtonClicked()

        verify(beatBox).play(sound)
    }

}