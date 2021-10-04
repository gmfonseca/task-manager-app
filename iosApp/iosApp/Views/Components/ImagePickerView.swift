//
//  ImagePickerView.swift
//  iosApp
//
//  Created by Gabriel Fonseca on 03/10/21.
//  Copyright © 2021 orgName. All rights reserved.
//

import UIKit
import SwiftUI

struct ImagePickerView: UIViewControllerRepresentable {

    @Environment(\.presentationMode) var isPresented
    var sourceType: UIImagePickerController.SourceType
    
    let onSelectedImage: (UIImage) -> Void
    
    func makeUIViewController(context: Context) -> UIImagePickerController {
        let imagePicker = UIImagePickerController()
        imagePicker.sourceType = self.sourceType
        imagePicker.delegate = context.coordinator
        return imagePicker
    }
    
    func updateUIViewController(_ uiViewController: UIImagePickerController, context: Context) {
        
    }
    
    func makeCoordinator() -> Coordinator {
        return Coordinator(picker: self, onSelectedImage: onSelectedImage)
    }
}

class Coordinator: NSObject, UINavigationControllerDelegate, UIImagePickerControllerDelegate {
    var picker: ImagePickerView
    
    var onSelectedImage: (UIImage) -> Void

    init(picker: ImagePickerView, onSelectedImage: @escaping (UIImage) -> Void) {
        self.picker = picker
        self.onSelectedImage = onSelectedImage
    }

    func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey : Any]) {

        guard let selectedImage = info[.originalImage] as? UIImage else { return }

        self.picker.isPresented.wrappedValue.dismiss()
        onSelectedImage(selectedImage)
    }
}
