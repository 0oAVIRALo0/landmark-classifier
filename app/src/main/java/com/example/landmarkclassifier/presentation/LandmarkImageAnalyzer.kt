package com.example.landmarkclassifier.presentation

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.landmarkclassifier.domain.Classification
import com.example.landmarkclassifier.domain.LandmarkClassifier

class LandmarkImageAnalyzer(
    private val landmarkClassifier: LandmarkClassifier,
    private val onLandmarkDetected: (List<Classification>) -> Unit
): ImageAnalysis.Analyzer {

    private var framSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if (framSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
                .centerCrop(321, 321)

            val results = landmarkClassifier.classify(bitmap, rotationDegrees)
            onLandmarkDetected(results)
        }

        framSkipCounter++

        image.close()
    }
}