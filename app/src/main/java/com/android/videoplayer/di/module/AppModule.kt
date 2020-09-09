package com.android.videoplayer.di.module

import com.android.videoplayer.repository.VideoRepository
import com.android.videoplayer.useCase.DeleteVideoUseCase
import com.android.videoplayer.useCase.GetVideoUseCase
import com.android.videoplayer.useCase.SaveVideoUseCase
import com.android.videoplayer.useCase.UpdateVideoUseCase
import com.android.videoplayer.viewmodel.PlayerViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    viewModel { PlayerViewModel(get(), get(), get(), get()) }

    single { createGetVideoUseCase(get()) }

    single { createDeleteVideoUseCase(get()) }

    single { createSaveVideoDataUseCase(get()) }

    single { createUpdateVideoDataUseCase(get()) }

    single { createVideoPlayerRepository(get()) }
}


fun createSaveVideoDataUseCase(
    videoRepository: VideoRepository
): SaveVideoUseCase {
    return SaveVideoUseCase(videoRepository)
}

fun createDeleteVideoUseCase(
    videoRepository: VideoRepository
): DeleteVideoUseCase {
    return DeleteVideoUseCase(videoRepository)
}

fun createUpdateVideoDataUseCase(
    videoRepository: VideoRepository
): UpdateVideoUseCase {
    return UpdateVideoUseCase(videoRepository)
}

fun createGetVideoUseCase(
    videoRepository: VideoRepository
): GetVideoUseCase {
    return GetVideoUseCase(videoRepository)
}
