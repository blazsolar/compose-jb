/*
 * Copyright 2020-2021 JetBrains s.r.o. and respective authors and developers.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE.txt file.
 */

package org.jetbrains.compose.desktop.ide.preview

import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.service
import com.intellij.openapi.externalSystem.model.execution.ExternalSystemTaskExecutionSettings
import com.intellij.openapi.externalSystem.service.execution.ProgressExecutionMode
import com.intellij.openapi.externalSystem.task.TaskCallback
import com.intellij.openapi.externalSystem.util.ExternalSystemUtil.runTask
import com.intellij.openapi.util.UserDataHolderBase
import org.jetbrains.plugins.gradle.settings.GradleSettings
import org.jetbrains.plugins.gradle.util.GradleConstants
import java.util.concurrent.CompletableFuture

class RunPreviewAction(
    private val fqName: String,
    private val modulePath: String
) : AnAction({ "Show non-interactive preview" }, PreviewIcons.RUN_PREVIEW) {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project!!
        val gradleVmOptions = GradleSettings.getInstance(project).gradleVmOptions
        val settings = ExternalSystemTaskExecutionSettings()
        settings.executionName = "Preview: $fqName"
        settings.externalProjectPath = modulePath
        settings.taskNames = listOf("configureDesktopPreview")
        settings.vmOptions = gradleVmOptions
        settings.externalSystemIdString = GradleConstants.SYSTEM_ID.id
        val gradleCallbackPort = project.service<PreviewStateService>().gradleCallbackPort
        settings.scriptParameters =
            listOf(
                "-Pcompose.desktop.preview.target=$fqName",
                "-Pcompose.desktop.preview.ide.port=$gradleCallbackPort"
            ).joinToString(" ")
        val future = CompletableFuture<Nothing>()
        runTask(
            settings,
            DefaultRunExecutor.EXECUTOR_ID,
            project,
            GradleConstants.SYSTEM_ID,
            object : TaskCallback {
                override fun onSuccess() {
                    future.complete(null)
                }
                override fun onFailure() {
                    future.completeExceptionally(RuntimeException("Preview for $fqName failed"))
                }
            },
            ProgressExecutionMode.IN_BACKGROUND_ASYNC,
            false,
            UserDataHolderBase()
        )
    }
}