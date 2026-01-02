package view;
public class TeacherProfile extends javax.swing.JPanel {
    public TeacherProfile() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        photo = new javax.swing.JLabel();
        lastName = new javax.swing.JLabel();
        lastNameTextField = new javax.swing.JTextField();
        firstName = new javax.swing.JLabel();
        firstNameTextField = new javax.swing.JTextField();
        email = new javax.swing.JLabel();
        emailTextField = new javax.swing.JTextField();
        gender = new javax.swing.JLabel();
        genderTextField = new javax.swing.JTextField();
        contact = new javax.swing.JLabel();
        contactTextField = new javax.swing.JTextField();
        address = new javax.swing.JLabel();
        addressTextField = new javax.swing.JTextField();
        department = new javax.swing.JLabel();
        departmentTextField = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(910, 630));
        setMinimumSize(new java.awt.Dimension(910, 630));

        photo.setBackground(new java.awt.Color(255, 255, 255));
        photo.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        photo.setForeground(new java.awt.Color(0, 0, 0));
        photo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        photo.setText("Profile");

        lastName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lastName.setText("Last Name");

        lastNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lastNameTextField.setText("  Random Name");
        lastNameTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        lastNameTextField.setRequestFocusEnabled(false);
        lastNameTextField.addActionListener(this::lastNameTextFieldActionPerformed);

        firstName.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        firstName.setText("FIrst Name");

        firstNameTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        firstNameTextField.setText("  Random Name");
        firstNameTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        firstNameTextField.setRequestFocusEnabled(false);
        firstNameTextField.addActionListener(this::firstNameTextFieldActionPerformed);

        email.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        email.setText("Email");

        emailTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        emailTextField.setText("  Random Name");
        emailTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        emailTextField.setRequestFocusEnabled(false);
        emailTextField.addActionListener(this::emailTextFieldActionPerformed);

        gender.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        gender.setText("Gender");

        genderTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        genderTextField.setText("  Random Name");
        genderTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        genderTextField.setRequestFocusEnabled(false);
        genderTextField.addActionListener(this::genderTextFieldActionPerformed);

        contact.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        contact.setText("Contact");

        contactTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        contactTextField.setText("  Random Name");
        contactTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        contactTextField.setRequestFocusEnabled(false);
        contactTextField.addActionListener(this::contactTextFieldActionPerformed);

        address.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        address.setText("Address");

        addressTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        addressTextField.setText("  Random Name");
        addressTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        addressTextField.setRequestFocusEnabled(false);
        addressTextField.addActionListener(this::addressTextFieldActionPerformed);

        department.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        department.setText("Dapartment");

        departmentTextField.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        departmentTextField.setText("  Random Name");
        departmentTextField.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        departmentTextField.setRequestFocusEnabled(false);
        departmentTextField.addActionListener(this::departmentTextFieldActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(350, 350, 350)
                .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(genderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gender, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(contactTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(address, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addressTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(department, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 263, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(departmentTextField))))
                .addGap(50, 50, 50))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gender, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(genderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(contactTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(department, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(departmentTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(140, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void lastNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lastNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lastNameTextFieldActionPerformed

    private void firstNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_firstNameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_firstNameTextFieldActionPerformed

    private void emailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_emailTextFieldActionPerformed

    private void genderTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_genderTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_genderTextFieldActionPerformed

    private void contactTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contactTextFieldActionPerformed

    private void addressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addressTextFieldActionPerformed

    private void departmentTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_departmentTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_departmentTextFieldActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel address;
    private javax.swing.JTextField addressTextField;
    private javax.swing.JLabel contact;
    private javax.swing.JTextField contactTextField;
    private javax.swing.JLabel department;
    private javax.swing.JTextField departmentTextField;
    private javax.swing.JLabel email;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JLabel firstName;
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JLabel gender;
    private javax.swing.JTextField genderTextField;
    private javax.swing.JLabel lastName;
    private javax.swing.JTextField lastNameTextField;
    private javax.swing.JLabel photo;
    // End of variables declaration//GEN-END:variables
  public void setFirstName(String firstName)
  {
      firstNameTextField.setText("  "+firstName);
  }
    public void setLastName(String lastName)
    {
        lastNameTextField.setText("  "+lastName);
    }

  public void setEmail(String email)
  {
      emailTextField.setText("  "+email);
  }

  public void setAddress(String address)
  {
      addressTextField.setText("  "+address);
  }

  public void setContact(String contact)
  {
      contactTextField.setText("  "+contact);
  }

  public void setGender(String gender)
  {
      genderTextField.setText("  "+gender);
  }

 

   public void setDepartment(String batch)
  {
      departmentTextField.setText("  "+batch);
  }
   
  
}
